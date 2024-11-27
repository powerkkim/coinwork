import { instance } from './index';

const apiGetUsers = () => {
	return instance.get('/api/v1/users');
};

export { apiGetUsers };
