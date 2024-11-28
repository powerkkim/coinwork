import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '@/views/HomeView.vue';

const routes = [
	{
		path: '/',
		redirect: '/dashboard',
	},
	{
		path: '/',
		name: 'appLayout',
		component: () => import('@/layout/AppLayout.vue'),
		children: [
			{
				path: '/dashboard',
				name: 'dashboard',
				component: () => import('@/views/Dashboard.vue'),
			},
			{
				path: '/utilities/base64view',
				name: 'base64view',
				component: () => import('@/views/Utilities/Base64View.vue'),
			},
			{
				path: '/utilities/QRGenerateView',
				name: 'QRGenerateView',
				component: () => import('@/views/Utilities/QRGenerateView.vue'),
			},
			{
				path: '/utilities/ScoreBoard',
				name: 'ScoreBoard',
				component: () => import('@/views/Utilities/ScoreBoard.vue'),
			},
		],
	},
	{
		path: '/',
		name: 'defaultLayout',
		component: () => import('@/layout/DefaultLayout.vue'),
		children: [
			{
				path: '/home',
				name: 'home',
				component: HomeView,
			},
			{
				path: '/about',
				name: 'about',
				component: () => import('@/views/AboutView.vue'),
			},
		],
	},
	{
		path: '/',
		name: 'emptyLayout',
		component: () => import('@/layout/EmptyLayout.vue'),
		children: [
			// {
			// 	path: '/home',
			// 	name: 'home',
			// 	component: HomeView,
			// },
			{
				path: '/:pathMatch(.*)*',
				component: () => import('@/views/NotFoundPage.vue'),
			},
		],
	},
];

const router = createRouter({
	history: createWebHistory(import.meta.env.BASE_URL),
	routes,
});

// router.beforeEach((to, from, next) => {
// 	if (to.meta.requiresAuth && !userStore().isLogin()) {
// 		next({ name: 'login' });
// 	} else {
// 		next();
// 	}
// });

export default router;
