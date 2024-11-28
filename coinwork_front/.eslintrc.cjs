/* eslint-env node */
require('@rushstack/eslint-patch/modern-module-resolution');

module.exports = {
	root: true,
	extends: [
		'plugin:vue/vue3-essential',
		'eslint:recommended',
		'@vue/eslint-config-prettier', //  '@vue/eslint-config-prettier/skip-formatting'  skip-formatting 을 제거
	],
	parserOptions: {
		ecmaVersion: 'latest',
	},
	rules: {
		'vue/multi-word-component-names': 'off',
		'vue/no-reserved-component-names': 'off',
		'vue/component-tags-order': [
			'error',
			{
				order: ['script', 'template', 'style'],
			},
		],
		'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
		// prettier 설정 적용
		'prettier/prettier': [
			'error',
			{
				singleQuote: true,
				semi: true,
				useTabs: true,
				tabWidth: 2,
				trailingComma: 'all',
				printWidth: 80,
				vueIndentScriptAndStyle: false,
				bracketSpacing: true,
				arrowParens: 'always',
				endOfLine: 'auto',
			},
		],
	},
};
