<script lang="ts">
  import Router from 'svelte-spa-router';
  import { routes } from './routes.js';
</script>

<main>
  <Router {routes} />
</main>

<style>
  /* Add your global styles here */
</style>