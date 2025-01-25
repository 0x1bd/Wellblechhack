<script>
    export let setting;
</script>

<style>
    .slider-container {
        flex: 1;
        position: relative;
        margin-left: 1.5rem;
    }

    input[type="range"] {
        width: 100%;
        height: 4px;
        appearance: none;
        -webkit-appearance: none;
        background: rgba(149, 76, 233, 0.2);
        border-radius: 2px;
    }

    input[type="range"]::-webkit-slider-thumb {
        -webkit-appearance: none;
        width: 18px;
        height: 18px;
        background: #954CE9;
        border-radius: 50%;
        cursor: pointer;
        transition: all 0.2s ease;
        box-shadow: 0 0 10px rgba(149, 76, 233, 0.5);
    }

    .value-display {
        position: absolute;
        right: 0;
        top: -27px;
        background: #954CE9;
        color: white;
        padding: 4px 10px;
        border-radius: 12px;
        font-size: 0.85rem;
        font-weight: 500;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
    }
</style>

<div class="slider-container">
    <input
        type="range"
        bind:value={setting.value}
        min={setting.min}
        max={setting.max}
        step={setting.step || 0.1}
    />
    <div class="value-display">{setting.value.toFixed(1)}</div>
</div>