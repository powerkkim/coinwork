<script setup>
import { ref } from 'vue';

const homeTeam = ref('Home');
const awayTeam = ref('Away');
const homeScore = ref(0);
const awayScore = ref(0);
const homeSets = ref(0);
const awaySets = ref(0);
const isCardVisible = ref(true);
const homeTeamEditing = ref(false);
const awayTeamEditing = ref(false);
const isExpanded = ref(false);

const incrementScore = (team) => {
	if (team === 'home') {
		homeScore.value++;
	} else {
		awayScore.value++;
	}
};

const decrementScore = (team) => {
	if (team === 'home' && homeScore.value > 0) {
		homeScore.value--;
	} else if (team === 'away' && awayScore.value > 0) {
		awayScore.value--;
	}
};

const incrementSet = (team) => {
	if (team === 'home') {
		homeSets.value++;
	} else {
		awaySets.value++;
	}
};

const decrementSet = (team) => {
	if (team === 'home' && homeSets.value > 0) {
		homeSets.value--;
	} else if (team === 'away' && awaySets.value > 0) {
		awaySets.value--;
	}
};

const toggleEdit = (team) => {
	if (team === 'home') {
		homeTeamEditing.value = !homeTeamEditing.value;
	} else {
		awayTeamEditing.value = !awayTeamEditing.value;
	}
};

const toggleExpand = () => {
	isExpanded.value = !isExpanded.value;
};
</script>

<template>
	<div>
		<Button
			@click="toggleExpand"
			:icon="isExpanded ? 'pi pi-compress' : 'pi pi-expand'"
			class="p-button-rounded p-button-text expand-button"
			:class="{ expanded: isExpanded }"
		/>
		<div :class="['scoreboard-container', { expanded: isExpanded }]">
			<div class="scoreboard">
				<div class="team home">
					<div class="team-name-container">
						<InputText
							v-model="homeTeam"
							placeholder="Home team"
							class="team-name-input"
							:readonly="!homeTeamEditing"
							:class="{ editing: homeTeamEditing }"
						/>
						<Button
							@click="toggleEdit('home')"
							:icon="homeTeamEditing ? 'pi pi-check' : 'pi pi-pencil'"
							class="p-button-rounded p-button-text edit-button"
							:class="{ 'p-button-success': homeTeamEditing }"
						/>
					</div>
					<div class="score-wrapper">
						<div @click="incrementScore('home')" class="score-button">
							<span class="score-team">{{ homeScore }}</span>
						</div>
						<Button
							@click="decrementScore('home')"
							icon="pi pi-minus"
							class="decrement-button p-button-rounded p-button-secondary"
						/>
					</div>
				</div>

				<div class="team away">
					<div class="team-name-container">
						<InputText
							v-model="awayTeam"
							placeholder="Away team"
							class="team-name-input"
							:readonly="!awayTeamEditing"
							:class="{ editing: awayTeamEditing }"
						/>
						<Button
							@click="toggleEdit('away')"
							:icon="awayTeamEditing ? 'pi pi-check' : 'pi pi-pencil'"
							class="p-button-rounded p-button-text edit-button"
							:class="{ 'p-button-success': awayTeamEditing }"
						/>
					</div>
					<div class="score-wrapper">
						<div @click="incrementScore('away')" class="score-button">
							<span class="score-team">{{ awayScore }}</span>
						</div>
						<Button
							@click="decrementScore('away')"
							icon="pi pi-minus"
							class="decrement-button p-button-rounded p-button-secondary"
						/>
					</div>
				</div>
			</div>
			<div class="sets">
				<div class="set-score">
					<span>{{ homeSets }}</span>
					<span class="score-separator">-</span>
					<span>{{ awaySets }}</span>
				</div>
				<div class="set-buttons">
					<div class="set-button-group">
						<Button
							@click="incrementSet('home')"
							label="add Home Set +"
							class="p-button"
						/>
						<Button
							@click="decrementSet('home')"
							icon="pi pi-minus"
							class="p-button p-button-secondary decrement-set-button"
						/>
					</div>
					<div class="set-button-group">
						<Button
							@click="incrementSet('away')"
							label="Add Away Set +"
							class="p-button"
						/>
						<Button
							@click="decrementSet('away')"
							icon="pi pi-minus"
							class="p-button p-button-secondary decrement-set-button"
						/>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<style scoped>
.scoreboard {
	display: flex;
	flex-direction: column;
	align-items: center;
	padding: 10px;
	width: 100%;
	/* height: calc(100% - 20px); */
	/* max-width: 400px; */
	margin: 0 auto;
}

.team {
	width: 100%;
	min-width: 300px;
	padding: 10px;
}

.team-name-container {
	display: flex;
	align-items: center;
	margin-bottom: 10px;
	width: 100%;
}

.team-name-input {
	flex-grow: 1;
	padding: 5px;
	font-size: 2rem;
	text-align: center;
	border: none;
	background-color: transparent;
}

.team-name-input.editing {
	background-color: #f0f0f0;
	border: 1px solid #ccc;
	border-radius: 4px;
}

.edit-button {
	width: 2rem;
	height: 2rem;
	margin-left: 5px;
}

.score-wrapper {
	position: relative;
	width: 100%;
}

.score-button {
	cursor: pointer;
	background-color: #f0f0f0;
	border-radius: 10px;
	width: 100%;
	aspect-ratio: 1.5 / 1;
	display: flex;
	justify-content: center;
	align-items: center;
	font-size: 4rem;
	font-weight: bold;
	user-select: none;
}

.decrement-button {
	position: absolute;
	bottom: 5px;
	right: 5px;
	width: 2rem;
	height: 2rem;
}

.sets {
	display: flex;
	flex-direction: column;
	/* align-items: center; */
	width: 100%;
	padding: 12px;
}

.set-score {
	display: flex;
	justify-content: center;
	font-size: 2rem;
	font-weight: bold;
	margin-bottom: 10px;
}

.score-separator {
	margin: 0 10px;
}

.set-buttons {
	display: flex;
	flex-direction: column;
	gap: 10px;
}

.set-button-group {
	display: flex;
	width: 100%;
}

.set-button-group .p-button:first-child {
	flex-grow: 1;
}

.decrement-set-button {
	margin-left: 5px;
	width: 2rem;
}

.scoreboard-container {
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	width: 100%;
	transition: all 0.3s ease;
}

.scoreboard-container.expanded {
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	z-index: 1000;
	background-color: #ffffff;
	padding: 20px;
}

.expand-button {
	position: absolute;
	top: 10px;
	right: 10px;
	z-index: 1001;
	background-color: rgba(255, 255, 255, 0.8);
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.expand-button:hover {
	background-color: rgba(255, 255, 255, 1);
}

.expand-button.expanded {
	position: fixed;
}
.score-team {
	font-size: 20vw;
}
.set-score {
	font-size: 4vw;
}

.set-buttons {
	flex-direction: row;
	justify-content: center;
}

.set-button-group {
	width: auto;
	margin: 0 10px;
}

@media (min-width: 768px) {
	.scoreboard {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		width: calc(100% - 20px);
		/* height: calc(100% - 20px); */
	}

	.team {
		width: 48%;
		min-width: 300px;
		margin-bottom: 0;
	}
}

@media (min-width: 1024px) {
	.scoreboard-container.expanded .scoreboard {
		flex-direction: row;
		justify-content: space-around;
		max-width: none;
		width: 100%;
	}

	.scoreboard-container.expanded .team {
		width: 40%;
	}

	.scoreboard-container.expanded .score-button {
		font-size: 6vw;
	}
}
</style>
