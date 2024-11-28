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
		base: '/', // ���� URL �� ����Ʈ�� ���⼭ BASE_URL�� �����մϴ�.
		// base: '/my-app/', // ���⼭ BASE_URL�� �����մϴ�.
		resolve: {
			alias: {
				'@': fileURLToPath(new URL('./src', import.meta.url)),
			},
		},
		// server: {
		// 	port: 8080,
		// },
		build: {
			outDir: '../src/main/resources/static/', // static ���� ���� dist ���丮�� ����
			emptyOutDir: true, // ���� �� ��� ���丮�� ���
		},
		css: {
			preprocessorOptions: {
				scss: {
					// ���ο� Sass ��� API ���
					additionalData: `@use "sass:math";`,
				},
			},
		},
	};

	if (mode === 'development') {
		config.build.sourcemap = true; // ����ȯ�濡�� �ҽ��� ����
		config.build.minify = false; // ����ȯ�濡���� �̴����� ��Ȱ��ȭ
	}

	return config;
});
