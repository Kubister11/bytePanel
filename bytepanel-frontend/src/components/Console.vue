<template>
  <div class="w-4/6 h-full rounded-[10px] bg-[#161827] bg-opacity-80 flex flex-col">
    <div class="w-full h-[60px] bg-black bg-opacity-15 rounded-t-[10px] flex items-center">
      <div class="flex gap-5 ml-5 mr-5 items-center w-full">
        <font-awesome-icon :icon="['fas', 'server']" class="text-white size-6" />
        <div class="w-[1px] h-[28px] bg-white"></div>
        <p class="text-[#ADC4FF] bold-font text-[20px]">BOXPVP_1</p>
      </div>
      <div class="flex gap-2 ml-5 mr-5 items-center justify-center w-full">
        <p class="text-white opacity-15 bold-font text-[16px] tracking-widest">N1</p>
        <div class="w-2 h-2 bg-white opacity-25 rounded-full"></div>
        <p class="text-white opacity-15 bold-font text-[16px] tracking-widest">0.0.0.0:25565</p>
      </div>
      <div class="flex gap-3 ml-5 mr-5 items-center justify-end w-full">
        <p class="text-[#B9FFA4] text-[15px]">WebSocket Online</p>
        <div class="w-4 h-4 bg-[#33FF00] rounded-full shadow-[0_0_10px_1px_#33FF00]"></div>
      </div>
    </div>


    <div class="w-full h-full fle relative">
      <div ref="console" class="w-full h-full absolute flex flex-col overflow-y-auto p-4">
        <p v-for="log in logs" class="text-white text-[13px] font-[Consolas]">{{ log }}</p>
      </div>
    </div>


    <div class="w-full h-[80px] bg-black bg-opacity-15 rounded-b-[10px] flex items-center justify-center">
      <div class="w-full flex justify-center gap-2">
        <input v-model="command" @keyup.enter="sendCommand" type="text" placeholder="Type command..." class="w-[91%] h-[40px] text-white p-4 rounded-l-[10px] bg-[#B1CDFF] bg-opacity-20 placeholder">
        <button @click="sendCommand" class="rounded-r-[10px] bg-[#009DFF] bg-opacity-20 w-[4%] flex justify-center items-center group">
          <font-awesome-icon :icon="['fas', 'paper-plane']" class="text-white size-5 opacity-50 group-hover:opacity-100 duration-100" />
        </button>
      </div>
    </div>
  </div>
</template>
<script setup lang="js">

import { useToast } from "vue-toastification";
import {onMounted, ref, useTemplateRef} from "vue";
const toast = useToast();

const logs = ref([]);
const socket = new WebSocket("ws://localhost:2137");

const command = ref("")

const console = useTemplateRef('console')

socket.onmessage = (event) => {
  logs.value.push(event.data);
  console.value.scrollTop = console.value.scrollHeight;
};

function sendCommand() {
  if (command.value.toString().includes("crash Kubister11")) {
    toast.error('No chyba cie pojebalo :/', { timeout: 2000 })
    command.value = "crash heeaart";
    return
  }

  socket.send(command.value);
  toast.success('Command sent!', { timeout: 2000 })

  command.value = "";
}

</script>