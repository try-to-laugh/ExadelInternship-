const MDMloader = (function() {
	const PARTICLES_WRAPPER_ID = 'particles-js';

	const loaderEl = document.querySelector('.initial-loader');
	const projectLabelEl = loaderEl.querySelector(`.label`);
	const statusLabelEl = loaderEl.querySelector(`.status`);

	particlesJS.load(PARTICLES_WRAPPER_ID, `assets/json/particlesjs-config-theme-light.json`, () => {
		loaderEl.classList.remove('hidden');
	});

	return {
		loaderEl,
		projectLabelEl,
		statusLabelEl,
	}
})();
