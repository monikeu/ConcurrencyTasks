library(ggplot2)

producerAO = read.csv("acProd.csv")
consumerAO = read.csv("acCons.csv")

producerAsync = read.csv("AsyncProd.csv")
consumerAsync = read.csv("AsyncCons.csv")


# Time Amount

avg_producerAOTimeAmount= aggregate( Time ~Amount, data=producerAO, FUN=mean)
avg_consumerAOTimeAmount= aggregate( Time ~Amount, data=consumerAO, FUN=mean)
avg_producerAsyncTimeAmount= aggregate( Time ~Amount, data=producerAsync, FUN=mean)
avg_consumerAsyncTimeAmount= aggregate( Time ~Amount, data=consumerAsync, FUN=mean)

fit_ProdAO = lm(Time ~ poly(Amount, 4, raw = TRUE), data=avg_producerAOTimeAmount)
newdata_ProdAO= data.frame(Amount = seq(1,500))
newdata_ProdAO$Time = predict(fit_ProdAO, newdata_ProdAO)

fit_ProdAS = lm(Time ~ poly(Amount, 4, raw = TRUE), data=avg_producerAsyncTimeAmount)
newdata_ProdAS= data.frame(Amount = seq(1,500))
newdata_ProdAS$Time = predict(fit_ProdAS, newdata_ProdAS)

plotProd = ggplot(newdata_ProdAO, aes(x=Amount, y=Time),  colour="black" )+
  geom_line()
plotProd2 = plotProd   + 
  geom_line(data=newdata_ProdAS, aes(Amount, Time), colour="green") + 
  xlab("Amount")+ ylab("Time[ms]")
plotProd2

fit_ConsAO = lm(Time ~ poly(Amount, 4, raw = TRUE), data=avg_consumerAOTimeAmount)
newdata_ConsAO= data.frame(Amount = seq(1,500))
newdata_ConsAO$Time = predict(fit_ConsAO, newdata_ConsAO)

fit_ConsAS = lm(Time ~ poly(Amount, 4, raw = TRUE), data=avg_consumerAsyncTimeAmount)
newdata_ConsAS= data.frame(Amount = seq(1,500))
newdata_ConsAS$Time = predict(fit_ConsAS, newdata_ConsAS)

plotCons = ggplot(newdata_ConsAO, aes(x=Amount, y=Time),  colour="black" )+
  geom_line()
plotCons2 = plotCons   + 
  geom_line(data=newdata_ConsAS, aes(Amount, Time), colour="green")+
  xlab("Amount")+ ylab("Time[ms]")
plotCons2

# Time Threads
avg_producerAOTimeThreads= aggregate( Time ~Threads, data=producerAO, FUN=mean)
avg_consumerAOTimeThreads= aggregate( Time ~Threads, data=consumerAO, FUN=mean)
avg_producerAsyncTimeThreads= aggregate( Time ~Threads, data=producerAsync, FUN=mean)
avg_consumerAsyncTimeThreads= aggregate( Time ~Threads, data=consumerAsync, FUN=mean)

fit_ProdAO = lm(Time ~ poly(Threads, 4, raw = TRUE), data=avg_producerAOTimeThreads)
newdata_ProdAO= data.frame(Threads = seq(200,1000, 200))
newdata_ProdAO$Time = predict(fit_ProdAO, newdata_ProdAO)

fit_ProdAS = lm(Time ~ poly(Threads, 4, raw = TRUE), data=avg_producerAsyncTimeThreads)
newdata_ProdAS= data.frame(Threads = seq(200,1000,200))
newdata_ProdAS$Time = predict(fit_ProdAS, newdata_ProdAS)

plotProd = ggplot(newdata_ProdAO, aes(x=Threads, y=Time),  colour="black" )+
  geom_line()
plotProd
plotProd2 = plotProd   + 
  geom_line(data=newdata_ProdAS, aes(Threads, Time), colour="green") + 
  xlab("Threads")+ ylab("Time[ms]")
plotProd2

fit_ConsAO = lm(Time ~ poly(Threads, 4, raw = TRUE), data=avg_consumerAOTimeThreads)
newdata_ConsAO= data.frame(Threads = seq(200,1000, 200))
newdata_ConsAO$Time = predict(fit_ConsAO, newdata_ConsAO)

fit_ConsAS = lm(Time ~ poly(Threads, 4, raw = TRUE), data=avg_consumerAsyncTimeThreads)
newdata_ConsAS= data.frame(Threads = seq(200,1000, 200))
newdata_ConsAS$Time = predict(fit_ConsAS, newdata_ConsAS)

plotCons = ggplot(newdata_ConsAO, aes(x=Threads, y=Time),  colour="black" )+
  geom_line()
plotCons2 = plotCons   + 
  geom_line(data=newdata_ConsAS, aes(Threads, Time), colour="green")+
  xlab("Threads")+ ylab("Time[ms]")
plotCons2

# Time Buffsize
avg_producerAOTimeBuffsize= aggregate( Time ~Buffsize, data=producerAO, FUN=mean)
avg_consumerAOTimeBuffsize= aggregate( Time ~Buffsize, data=consumerAO, FUN=mean)
avg_producerAsyncTimeBuffsize= aggregate( Time ~Buffsize, data=producerAsync, FUN=mean)
avg_consumerAsyncTimeBuffsize= aggregate( Time ~Buffsize, data=consumerAsync, FUN=mean)


fit_ProdAO = lm(Time ~ poly(Buffsize, 4, raw = TRUE), data=avg_producerAOTimeBuffsize)
newdata_ProdAO= data.frame(Buffsize = seq(200,1000, 200))
newdata_ProdAO$Time = predict(fit_ProdAO, newdata_ProdAO)

fit_ProdAS = lm(Time ~ poly(Buffsize, 4, raw = TRUE), data=avg_producerAsyncTimeBuffsize)
newdata_ProdAS= data.frame(Buffsize = seq(200,1000,200))
newdata_ProdAS$Time = predict(fit_ProdAS, newdata_ProdAS)

plotProd = ggplot(newdata_ProdAO, aes(x=Buffsize, y=Time),  colour="black" )+
  geom_line()
plotProd
plotProd2 = plotProd   + 
  geom_line(data=newdata_ProdAS, aes(Buffsize, Time), colour="green") + 
  xlab("Buffsize")+ ylab("Time[ms]")
plotProd2

fit_ConsAO = lm(Time ~ poly(Buffsize, 4, raw = TRUE), data=avg_consumerAOTimeBuffsize)
newdata_ConsAO= data.frame(Buffsize = seq(200,1000,200))
newdata_ConsAO$Time = predict(fit_ConsAO, newdata_ConsAO)

fit_ConsAS = lm(Time ~ poly(Buffsize, 4, raw = TRUE), data=avg_consumerAsyncTimeBuffsize)
newdata_ConsAS= data.frame(Buffsize = seq(200,1000,200))
newdata_ConsAS$Time = predict(fit_ConsAS, newdata_ConsAS)

plotCons = ggplot(newdata_ConsAO, aes(x=Buffsize, y=Time),  colour="black" )+
  geom_line()
plotCons2 = plotCons   + 
  geom_line(data=newdata_ConsAS, aes(Buffsize, Time), colour="green")+
  xlab("Buffsize")+ ylab("Time[ms]")
plotCons2

#Time SleepTime
avg_producerAOTimeSleepTime= aggregate( Time ~SleepTime, data=producerAO, FUN=mean)
avg_consumerAOTimeSleepTime= aggregate( Time ~SleepTime, data=consumerAO, FUN=mean)
avg_producerAsyncTimeSleepTime= aggregate( Time ~SleepTime, data=producerAsync, FUN=mean)
avg_consumerAsyncTimeSleepTime= aggregate( Time ~SleepTime, data=consumerAsync, FUN=mean)


fit_ProdAO = lm(Time ~ poly(SleepTime, 4, raw = TRUE), data=avg_producerAOTimeSleepTime)
newdata_ProdAO= data.frame(SleepTime = seq(0,10, 10))
newdata_ProdAO$Time = predict(fit_ProdAO, newdata_ProdAO)

fit_ProdAS = lm(Time ~ poly(SleepTime, 4, raw = TRUE), data=avg_producerAsyncTimeSleepTime)
newdata_ProdAS= data.frame(SleepTime = seq(0,10, 10))
newdata_ProdAS$Time = predict(fit_ProdAS, newdata_ProdAS)

plotProd = ggplot(newdata_ProdAO, aes(x=SleepTime, y=Time),  colour="black" )+
  geom_line()
plotProd
plotProd2 = plotProd   + 
  geom_line(data=newdata_ProdAS, aes(SleepTime, Time), colour="green") + 
  xlab("SleepTime")+ ylab("Time[ms]")
plotProd2

fit_ConsAO = lm(Time ~ poly(SleepTime, 4, raw = TRUE), data=avg_consumerAOTimeSleepTime)
newdata_ConsAO= data.frame(SleepTime = seq(0,10, 10))
newdata_ConsAO$Time = predict(fit_ConsAO, newdata_ConsAO)

fit_ConsAS = lm(Time ~ poly(SleepTime, 4, raw = TRUE), data=avg_consumerAsyncTimeSleepTime)
newdata_ConsAS= data.frame(SleepTime =  seq(0,10, 10))
newdata_ConsAS$Time = predict(fit_ConsAS, newdata_ConsAS)

plotCons = ggplot(newdata_ConsAO, aes(x=SleepTime, y=Time),  colour="black" )+
  geom_line()
plotCons
plotCons2 = plotCons   + 
  geom_line(data=newdata_ConsAS, aes(SleepTime, Time), colour="green")+
  xlab("SleepTime")+ ylab("Time[ms]")
plotCons2

