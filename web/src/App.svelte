<script lang="ts">
  import Router from 'svelte-spa-router';
  import { routes } from './routes.js';

  import { WebSocketEventBus, SomeEvent, AnotherEvent } from './lib/eventBus.js';

  const ws = new WebSocketEventBus('ws://localhost:8080/ws');

  function test() {
    ws.sendEvent(new SomeEvent("Hello from client!"));
  }

  ws.addListener<AnotherEvent>("AnotherEvent", (event) => {
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