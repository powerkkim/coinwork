import { fileURLToPath, URL } from 'node:url';

import { PrimeVueResolver } from '@primevue/auto-import-resolver';
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import Components from 'unplugin-vue-components/vite';

// https://vitejs.dev/config/
export default defineConfig(({ /*command,*/ mode }) => {
	const config = {
		plugins: [
			vue(),
			Components({
				resolvers: [PrimeVueResolver()],
			}),
		],
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
		build: {
			outDir: '../src/main/resources/static/', // static 폴더 안의 dist 디렉토리에 빌드
			emptyOutDir: true, // 빌드 전 출력 디렉토리를 비움
		},
		css: {
			preprocessorOptions: {
				scss: {
					// 새로운 Sass 모듈 API 사용
					additionalData: `@use "sass:math";`,
				},
			},
		},
	};

	if (mode === 'development') {
		config.build.sourcemap = true; // 개발환경에서 소스맵 생성
		config.build.minify = false; // 개발환경에서는 미니파이 비활성화
	}

	return config;
});
