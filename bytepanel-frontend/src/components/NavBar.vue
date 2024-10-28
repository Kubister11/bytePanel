<script setup>
import router from "@/router/index.js";

function routeToServers() {
  router.push({name: "servers"})
}
</script>

<template>
  <header class="w-full h-[56px] rounded-[10px] bg-black bg-opacity-20 drop-shadow-lg shadow-black flex">
    <div class="w-full flex justify-start items-center ml-5 gap-2">
      <h1 @click="routeToServers" class="text-white text-[32px] bold-font cursor-pointer">BytePanel</h1>
      <div class="flex h-full items-end">
        <p class="text-white opacity-15 bold-font text-[15px] mb-2.5">v1.0</p>
      </div>
    </div>

    <div class="w-full flex justify-end items-center mr-5">
      <font-awesome-icon :icon="['fas', 'user-tie']" class="text-white size-6 opacity-70" />
    </div>
  </header>
</template>