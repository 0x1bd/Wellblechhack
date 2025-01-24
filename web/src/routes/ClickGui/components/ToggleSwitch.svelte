<script>
    export let checked = false;
</script>

<style>
    :global(.sr-only) {
        position: absolute;
        width: 1px;
        height: 1px;
        padding: 0;
        margin: -1px;
        overflow: hidden;
        clip: rect(0, 0, 0, 0);
        white-space: nowrap;
        border: 0;
    }

    .toggle-switch {
        position: relative;
        display: inline-block;
        width: 48px;
        height: 26px;
        border-radius: 15px;
        background: rgba(149, 76, 233, 0.1);
        border: 1px solid rgba(149, 76, 233, 0.3);
        transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
    }

    .toggle-slider {
        position: absolute;
        cursor: pointer;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: transparent;
        transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        border-radius: 15px;
    }

    .toggle-slider:before {
        position: absolute;
        content: "";
        height: 20px;
        width: 20px;
        top: 50%;
        left: 2px;
        transform: translateY(-50%);
        background: rgba(240, 240, 245, 0.9);
        transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        border-radius: 50%;
        filter: drop-shadow(0 2px 4px rgba(0, 0, 0, 0.2));
    }

    input:checked + .toggle-slider {
        background: linear-gradient(135deg, #954CE9 0%, #7a3eb6 100%);
    }

    input:checked + .toggle-slider:before {
        transform: translate(22px, -50%);
        background: #ffffff;
    }
</style>

<label class="toggle-switch">
    <input type="checkbox" bind:checked class="sr-only">
    <span class="toggle-slider" />
</label>