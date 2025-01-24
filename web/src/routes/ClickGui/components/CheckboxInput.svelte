<script lang="ts">
    export let checked: boolean = false;
    export let label: string = "";
</script>

<style>
    .checkbox-container {
        display: flex;
        align-items: center;
        gap: 0.8rem;
        width: 100%;
    }

    .custom-checkbox {
        position: relative;
        width: 20px;
        height: 20px;
        border: 2px solid rgba(149, 76, 233, 0.4);
        border-radius: 5px;
        transition: all 0.3s ease;
        cursor: pointer;
    }

    .custom-checkbox::after {
        content: "✓";
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%) scale(0);
        color: #fff;
        font-size: 14px;
        transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);
    }

    input:checked + .custom-checkbox {
        background: #954CE9;
        border-color: #954CE9;
        box-shadow: 0 0 10px rgba(149, 76, 233, 0.3);
    }

    input:checked + .custom-checkbox::after {
        transform: translate(-50%, -50%) scale(1);
    }
</style>

<label class="checkbox-container">
    <input type="checkbox" bind:checked class="sr-only">
    <div class="custom-checkbox"></div>
    <span class="setting-label">{label}</span>
</label>