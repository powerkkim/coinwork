<script setup>
import { ref } from 'vue';

const originText = ref('');
const targetText = ref('');

function base64ToBytes(base64) {
	const binString = atob(base64);
	return Uint8Array.from(binString, (m) => m.codePointAt(0));
}

function bytesToBase64(bytes) {
	const binString = String.fromCodePoint(...bytes);
	return btoa(binString);
}

const isWellFormedBase64 = (str) => {
	if (typeof str.isWellFormed !== 'undefined') {
		return str.isWellFormed();
	} else {
		// 오래된 encodeURIComponent()를 사용합니다.
		try {
			encodeURIComponent(str);
			return true;
		} catch (error) {
			return false;
		}
	}
};

const clickEncodeBase64 = () => {
	if (!isWellFormedBase64(originText.value)) {
		alert('not support text.');
		return;
	}

	targetText.value = bytesToBase64(new TextEncoder().encode(originText.value));
	console.log(targetText.value);
};

const clickDecodeBase64 = () => {
	try {
		const origindecode = new TextDecoder().decode(
			base64ToBytes(targetText.value),
		);

		if (!isWellFormedBase64(origindecode)) {
			alert('not support text.');
			originText.value = '';
			console.log('Decode Text Not Valid');
			return;
		}

		const originEncod = bytesToBase64(new TextEncoder().encode(origindecode));

		if (originEncod == targetText.value) {
			originText.value = origindecode;
		} else {
			console.log(originEncod, targetText.value, 'Not Same');
			alert('not support text.');
			originText.value = '';
		}

		console.log(originText.value);
	} catch (error) {
		alert('not support text.');
	}
};
</script>

<template>
	<Card class="mb-8">
		<template #title>Base64 Encode/Decode</template>
		<template #content>
			<p class="m-0">Unicode conversion included.</p>
		</template>
	</Card>
	<Fluid>
		<div class="flex">
			<div class="card flex flex-col gap-4 w-full p-10">
				<div class="flex flex-wrap">
					<label for="OriginText">Origin Text ( Decoded Text )</label>
					<Textarea id="OriginText" rows="4" v-model="originText" />
				</div>

				<div class="flex gap-2">
					<Button
						label="Encode"
						@click="clickEncodeBase64"
						icon="pi pi-arrow-down"
						iconPos="right"
					/>
					<Button
						label="Decode"
						@click="clickDecodeBase64"
						icon="pi pi-arrow-up"
						iconPos="right"
					/>
				</div>

				<div class="flex flex-wrap">
					<label for="TargetText">Target Text ( Encoded Text)</label>
					<Textarea id="TargetText" rows="4" v-model="targetText" />
				</div>
			</div>
		</div>
	</Fluid>
</template>
