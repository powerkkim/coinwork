import { fileURLToPath, URL } from 'node:url';

import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import vueDevTools from 'vite-plugin-vue-devtools';

// https://vitejs.dev/config/
export default defineConfig(({ /*command,*/ mode }) => {
	const config = {
		plugins: [vue(), vueDevTools()],
		base: '/', // 정적 URL 내 사이트의 여기서 BASE_URL을 설정합니다.
		// base: '/my-app/', // 여기서 BASE_URL을 설정합니다.
		resolve: {
			alias: {
				'@': fileURLToPath(new URL('./src', import.meta.url)),
			},
		},
		// server: {
		// 	port: 8080,
		// },
		server: {
			proxy: {
				'/freetestapi': {
					target: 'https://freetestapi.com',
					changeOrigin: true,
					secure: false,
					rewrite: (path) => path.replace(/^\/freetestapi/, ''),
				},
			},
		},
		build: {
			outDir: '../src/main/resources/static/', // static 폴더 안의 dist 디렉토리에 빌드
			emptyOutDir: true, // 빌드 전 출력 디렉토리를 비움
		},
	};

	if (mode === 'development') {
		config.build.sourcemap = true; // 개발환경에서 소스맵 생성
		config.build.minify = false; // 개발환경에서는 미니파이 비활성화
	}

	return config;
});
