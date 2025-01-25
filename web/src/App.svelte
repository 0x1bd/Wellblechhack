<script lang="ts">
  import Router from 'svelte-spa-router';
  import { routes } from './routes.js';
</script>

<main>
  <Router {routes} />
</main>

<style>
  :root {
    font-family: Inter, system-ui, Avenir, Helvetica, Arial, sans-serif;
    line-height: 1.5;
    font-weight: 400;

    color: rgba(255, 255, 255, 0.87);
  }

  :global(html) {
    background: transparent !important;
    overflow: hidden;
  }

  * {
    user-select: none;
  }
</style>