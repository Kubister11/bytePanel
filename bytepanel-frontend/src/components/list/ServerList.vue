<script setup lang="js">

import {onMounted, reactive} from "vue";
import router from "@/router/index.js";
import { fetchServers } from "@/fetcher/backend.js";

const state = reactive({
  servers: []
})

onMounted(() => {
  fetchServers().then(response => {
    state.servers = response.data
  })
})


function routeToServerPanel(id) {
  router.push({name: "console", query: {id: id}})
}

</script>

<template>
  <div class="flex flex-wrap gap-3">
    <div v-for="server in state.servers" @click="routeToServerPanel(server._id)" data-aos="fade-up" class="bg-black flex w-[350px] h-[170px] rounded-[10px] bg-opacity-20 cursor-pointer gap-1">
      <div class="h-full w-[15px] bg-[#FFD900] rounded-l-[10px] bg-opacity-35"></div>

      <div class="h-full w-full flex-col flex p-3 gap-3">
        <h1 class="text-[#ADC4FF] text-[32px] bold-font leading-none">{{ server.name }}</h1>

        <div class="flex w-fit items-start flex-col gap-2">
          <div class="flex items-start flex-col">
            <p class="text-white">CPU Load</p>
            <p class="text-[#D1DEFF] bold-font">{{ server.cpuLoad }}% <span class="opacity-40">/ {{ server.cpuLoadMax }}%</span></p>
          </div>
          <div class="flex items-start flex-col">
            <p class="text-white">Memory Usage</p>
            <p class="text-[#D1DEFF] bold-font">{{ server.memoryUsage }}GB <span class="opacity-40">/ {{ server.memoryUsageMax }}GB</span></p>
          </div>
        </div>
      </div>

      <div class="h-full flex-col flex p-3 gap-3">
        <div class="h-full justify-end flex">
          <font-awesome-icon :icon="['fas', 'server']" class="text-white size-[70px] opacity-70" />
        </div>
        <div class="h-full flex items-end">
          <div class="flex gap-1.5">
            <div class="bg-[#009DFF] bg-opacity-20 flex items-center justify-center w-[40px] h-[40px] rounded-l-[10px]">
              <font-awesome-icon :icon="['fas', 'power-off']" class="text-white size-[25px] opacity-70" />
            </div>
            <div class="bg-[#009DFF] bg-opacity-20 flex items-center justify-center w-[40px] h-[40px]">
              <font-awesome-icon :icon="['fas', 'rotate']" class="text-white size-[25px] opacity-70" />
            </div>
            <div class="bg-[#009DFF] bg-opacity-20 flex items-center justify-center w-[40px] h-[40px] rounded-r-[10px]">
              <font-awesome-icon :icon="['far', 'circle-stop']" class="text-white size-[25px] opacity-70" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>