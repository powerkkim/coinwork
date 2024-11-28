<script setup>
import { ref, computed, watch } from 'vue';
import QRCode from 'qrcode';

const qrType = ref('URL');
const qrInputs = ref({});
const qrCodeUrl = ref('');

// QR 코드 크기 조절을 위한 ref 추가
const qrCodeSize = ref(200);
const inputText = ref('');

const qrTypes = [
	{ label: 'URL', value: 'URL' },
	{ label: 'TEL', value: 'TEL' },
	{ label: 'VCARD', value: 'VCARD' },
	{ label: 'TEXT', value: 'TEXT' },
	{ label: 'EMAIL', value: 'EMAIL' },
	{ label: 'SMS', value: 'SMS' },
	{ label: 'WIFI', value: 'WIFI' },
	{ label: 'GEO', value: 'GEO' },
	{ label: 'BITCOIN', value: 'BITCOIN' },
	{ label: 'TWITTER', value: 'TWITTER' },
	{ label: 'FACEBOOK', value: 'FACEBOOK' },
	{ label: 'PDF', value: 'PDF' },
	{ label: 'MP3', value: 'MP3' },
	{ label: 'APPSTORE', value: 'APPSTORE' },
	{ label: 'IMAGE', value: 'IMAGE' },
	{ label: 'EVENT', value: 'EVENT' },
];

const inputFields = computed(() => {
	switch (qrType.value) {
		case 'URL':
			return [{ key: 'url', label: 'URL', type: 'text' }];
		case 'TEL':
			return [{ key: 'number', label: 'Phone Number', type: 'tel' }];
		case 'VCARD':
			return [
				{ key: 'firstName', label: 'First Name', type: 'text' },
				{ key: 'lastName', label: 'Last Name', type: 'text' },
				{ key: 'phone', label: 'Phone', type: 'tel' },
				{ key: 'email', label: 'Email', type: 'email' },
				{ key: 'address', label: 'Address', type: 'text' },
			];
		case 'TEXT':
			return [{ key: 'text', label: 'Text', type: 'textarea' }];
		case 'EMAIL':
			return [
				{ key: 'email', label: 'Email', type: 'email' },
				{ key: 'subject', label: 'Subject', type: 'text' },
				{ key: 'body', label: 'Body', type: 'textarea' },
			];
		case 'SMS':
			return [
				{ key: 'number', label: 'Phone Number', type: 'tel' },
				{ key: 'message', label: 'Message', type: 'textarea' },
			];
		case 'WIFI':
			return [
				{ key: 'ssid', label: 'SSID', type: 'text' },
				{ key: 'password', label: 'Password', type: 'password' },
				{
					key: 'encryption',
					label: 'Encryption',
					type: 'select',
					options: [
						{ label: 'WEP', value: 'WEP' },
						{ label: 'WPA', value: 'WPA' },
						{ label: 'WPA2', value: 'WPA2' },
					],
				},
			];
		case 'GEO':
			return [
				{ key: 'latitude', label: 'Latitude', type: 'number' },
				{ key: 'longitude', label: 'Longitude', type: 'number' },
			];
		case 'BITCOIN':
			return [
				{ key: 'address', label: 'Bitcoin Address', type: 'text' },
				{ key: 'amount', label: 'Amount', type: 'number' },
			];
		case 'TWITTER':
			return [{ key: 'username', label: 'Twitter Username', type: 'text' }];
		case 'FACEBOOK':
			return [
				{ key: 'profileUrl', label: 'Facebook Profile URL', type: 'text' },
			];
		case 'PDF':
		case 'MP3':
		case 'APPSTORE':
		case 'IMAGE':
			return [{ key: 'fileUrl', label: 'File URL', type: 'text' }];
		case 'EVENT':
			return [
				{ key: 'summary', label: 'Event Summary', type: 'text' },
				{ key: 'startDate', label: 'Start Date', type: 'date' },
				{ key: 'endDate', label: 'End Date', type: 'date' },
				{ key: 'location', label: 'Location', type: 'text' },
			];
		default:
			return [{ key: 'text', label: 'Text', type: 'textarea' }];
	}
});

watch(qrType, () => {
	qrInputs.value = {};
	qrCodeUrl.value = '';
});

const generateQRCode = async () => {
	let qrData = '';
	switch (qrType.value) {
		case 'URL':
			qrData = qrInputs.value.url;
			break;
		case 'TEL':
			qrData = `tel:${qrInputs.value.number}`;
			break;
		case 'VCARD':
			qrData = `BEGIN:VCARD
VERSION:3.0
N:${qrInputs.value.lastName};${qrInputs.value.firstName}
TEL:${qrInputs.value.phone}
EMAIL:${qrInputs.value.email}
ADR:${qrInputs.value.address}
END:VCARD`;
			break;
		case 'TEXT':
			qrData = qrInputs.value.text;
			break;
		case 'EMAIL':
			qrData = `mailto:${qrInputs.value.email}?subject=${encodeURIComponent(qrInputs.value.subject)}&body=${encodeURIComponent(qrInputs.value.body)}`;
			break;
		case 'SMS':
			qrData = `SMSTO:${qrInputs.value.number}:${qrInputs.value.message}`;
			break;
		case 'WIFI':
			qrData = `WIFI:T:${qrInputs.value.encryption};S:${qrInputs.value.ssid};P:${qrInputs.value.password};;`;
			break;
		case 'GEO':
			qrData = `geo:${qrInputs.value.latitude},${qrInputs.value.longitude}`;
			break;
		case 'BITCOIN':
			qrData = `bitcoin:${qrInputs.value.address}?amount=${qrInputs.value.amount}`;
			break;
		case 'TWITTER':
			qrData = `https://twitter.com/${qrInputs.value.username}`;
			break;
		case 'FACEBOOK':
			qrData = qrInputs.value.profileUrl;
			break;
		case 'PDF':
		case 'MP3':
		case 'APPSTORE':
		case 'IMAGE':
			qrData = qrInputs.value.fileUrl;
			break;
		case 'EVENT':
			qrData = `BEGIN:VEVENT
SUMMARY:${qrInputs.value.summary}
DTSTART:${qrInputs.value.startDate.replace(/-/g, '')}
DTEND:${qrInputs.value.endDate.replace(/-/g, '')}
LOCATION:${qrInputs.value.location}
END:VEVENT`;
			break;
		default:
			qrData = qrInputs.value.text;
	}

	try {
		const url = await QRCode.toDataURL(qrData, {
			width: qrCodeSize.value,
			margin: 1,
			color: {
				dark: '#000000',
				light: '#ffffff',
			},
		});
		qrCodeUrl.value = url;
	} catch (error) {
		console.error('QR 코드 생성 중 오류 발생:', error);
	}
};

const downloadQRCode = () => {
	if (qrCodeUrl.value) {
		const link = document.createElement('a');
		link.download = 'qrcode.png';
		link.href = qrCodeUrl.value;
		link.click();
	}
};

// 아이콘을 가져오는 함수 추가
const getIconForType = (type) => {
	const iconMap = {
		URL: 'pi pi-link',
		TEL: 'pi pi-phone',
		VCARD: 'pi pi-id-card',
		TEXT: 'pi pi-file-edit',
		EMAIL: 'pi pi-envelope',
		SMS: 'pi pi-comments',
		WIFI: 'pi pi-wifi',
		GEO: 'pi pi-map-marker',
		BITCOIN: 'pi pi-bitcoin',
		TWITTER: 'pi pi-twitter',
		FACEBOOK: 'pi pi-facebook',
		PDF: 'pi pi-file-pdf',
		MP3: 'pi pi-volume-up',
		APPSTORE: 'pi pi-apple',
		IMAGE: 'pi pi-image',
		EVENT: 'pi pi-calendar',
	};
	return iconMap[type] || 'pi pi-question';
};

// QR 코드 크기 증가 함수
const increaseQRSize = () => {
	qrCodeSize.value = Math.min(qrCodeSize.value + 50, 400);
	generateQRCode();
};

// QR 코드 크기 감소 함수
const decreaseQRSize = () => {
	qrCodeSize.value = Math.max(qrCodeSize.value - 50, 100);
	generateQRCode();
};

watch(inputText, () => {
	generateQRCode();
});
</script>

<template>
	<Card class="mb-8">
		<template #title>QR Code Generator</template>
		<template #content>
			<p class="m-0">
				Select a QR code type and enter the required information.
			</p>
		</template>
	</Card>
	<Fluid>
		<div class="flex">
			<div class="card flex flex-col gap-4 w-full p-10">
				<div class="qr-type-selector">
					<div
						v-for="type in qrTypes"
						:key="type.value"
						class="qr-type-option"
						:class="{ selected: qrType === type.value }"
						@click="qrType = type.value"
					>
						<i :class="getIconForType(type.value)"></i>
						<span>{{ type.label }}</span>
					</div>
				</div>

				<div
					v-for="field in inputFields"
					:key="field.key"
					class="flex flex-wrap"
				>
					<label :for="field.key">{{ field.label }}</label>
					<template v-if="field.type === 'select'">
						<Dropdown
							:id="field.key"
							v-model="qrInputs[field.key]"
							:options="field.options"
							optionLabel="label"
							optionValue="value"
							class="w-full"
						/>
					</template>
					<template v-else-if="field.type === 'textarea'">
						<Textarea
							:id="field.key"
							v-model="qrInputs[field.key]"
							:rows="4"
							class="w-full"
						/>
					</template>
					<template v-else>
						<InputText
							:id="field.key"
							v-model="qrInputs[field.key]"
							:type="field.type"
							class="w-full"
						/>
					</template>
				</div>

				<div class="flex gap-2">
					<Button
						label="Generate QR Code"
						@click="generateQRCode"
						icon="pi pi-check"
						iconPos="right"
					/>
					<Button
						label="Download QR Code"
						@click="downloadQRCode"
						icon="pi pi-download"
						iconPos="right"
						:disabled="!qrCodeUrl"
					/>
				</div>

				<div
					class="flex flex-wrap justify-content-center align-items-center"
					v-if="qrCodeUrl"
				>
					<div class="qr-size-controls ml-4">
						<h3>Change Size</h3>
						<div class="flex flex-column align-items-center">
							<Button
								icon="pi pi-plus"
								@click="increaseQRSize"
								class="p-button-rounded p-button-text mb-2"
							/>
							<Button
								icon="pi pi-minus"
								@click="decreaseQRSize"
								class="p-button-rounded p-button-text"
							/>
						</div>
					</div>
					<div class="qr-code-container">
						<img :src="qrCodeUrl" alt="생성된 QR 코드" class="qr-code-image" />
					</div>
				</div>
			</div>
		</div>
	</Fluid>
</template>

<style scoped>
.qr-code-image {
	max-width: 400px;
	max-height: 400px;
}

.qr-type-selector {
	display: grid;
	grid-template-columns: repeat(auto-fill, minmax(80px, 1fr));
	gap: 0.5rem;
	margin-bottom: 1rem;
}

.qr-type-option {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	padding: 0.5rem;
	border: 1px solid var(--surface-border);
	border-radius: 0.5rem;
	cursor: pointer;
	transition: all 0.2s ease;
	background-color: var(--surface-card);
	height: 80px;
}

.qr-type-option:hover {
	background-color: var(--surface-hover);
}

.qr-type-option.selected {
	background-color: var(--primary-color);
	color: var(--primary-color-text);
	border-color: var(--primary-color);
}

.qr-type-option i {
	font-size: 1.25rem;
	margin-bottom: 0.25rem;
}

.qr-type-option span {
	font-size: 0.75rem;
	text-align: center;
	word-break: break-word;
	line-height: 1;
}

.qr-size-controls {
	background-color: var(--surface-card);
	padding: 1rem;
	border-radius: 0.5rem;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	width: 100px; /* 고정 너비 설정 */
	height: 100px; /* 고정 너비 설정 */
}

.qr-size-controls h3 {
	margin-bottom: 0.5rem;
	font-size: 1rem;
	color: var(--text-color);
}
</style>
