<script lang="ts">
  import Router from 'svelte-spa-router';
  import { routes } from './routes.js';

  import { SomeEvent, AnotherEvent, bus, ClickGuiInfoEvent } from './lib/eventBus.js';
    import { categories } from './routes/ClickGui/data.js';
  
  function test() {
    bus.sendEvent(new SomeEvent("Hello from client!"));
  }

  bus.addListener<AnotherEvent>("AnotherEvent", (event) => {
    console.log("Rec: " + event.value);
  });
</script>

<main>
  <button onclick={test}>Cock</button>
  <Router {routes}></Router>
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