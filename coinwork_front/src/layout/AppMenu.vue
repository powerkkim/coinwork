<script setup>
import { ref } from 'vue';

import AppMenuItem from './AppMenuItem.vue';

const model = ref([
	{
		label: 'Home',
		items: [{ label: 'Dashboard', icon: 'pi pi-fw pi-home', to: '/dashboard' }],
	},
	{
		label: 'Utilities',
		items: [
			{
				label: 'Base64 Encode / Decode',
				icon: 'pi pi-fw pi-code',
				to: '/utilities/base64view',
			},
			{
				label: 'QRCode Generator',
				icon: 'pi pi-fw pi-qrcode',
				to: '/utilities/QRGenerateView',
			},
			{
				label: 'Score Board',
				icon: 'pi pi-fw pi-star',
				to: '/utilities/ScoreBoard',
			},
			{ label: 'Misc', icon: 'pi pi-fw pi-circle', to: '/uikit/misc' },
		],
	},
	{
		label: 'Pages',
		icon: 'pi pi-fw pi-briefcase',
		to: '/pages',
		items: [
			{
				label: 'Landing',
				icon: 'pi pi-fw pi-globe',
				to: '/landing',
			},
		],
	},
]);
</script>

<template>
	<ul class="layout-menu">
		<template v-for="(item, i) in model" :key="item">
			<app-menu-item
				v-if="!item.separator"
				:item="item"
				:index="i"
			></app-menu-item>
			<li v-if="item.separator" class="menu-separator"></li>
		</template>
	</ul>
</template>

<style lang="scss" scoped></style>
