package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_B2_AyaMemoriaMediumTrap extends Card {

    public SIGNI_B2_AyaMemoriaMediumTrap()
    {
        setImageSets("WXDi-P09-066", "WXDi-P09-066P", "SPDi01-93");

        setOriginalName("中罠　あや//メモリア");
        setAltNames("チュウビンアヤメモリア Chuubin Aya Memoria");
        setDescription("jp",
                "@A $T1 %B0：あなたの手札を１枚ルリグゾーンに裏向きで置く。次の対戦相手のターン終了時まで、あなたは@>@A -M -A %B0：この方法で置いたカードをルリグゾーンから手札に加える。@@を得る。次の対戦相手のターン終了時、そのカードを手札に加える。\n" +
                "@A $T1 #C #C：カードを１枚引く。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );

        setName("en", "Aya//Memoria, Medium Trickster");
        setDescription("en",
                "@A $T1 %B0: Put a card from your hand face down into your LRIG Zone. You gain@>@A -M -A %B0: Add the card put into the LRIG Zone this way from your LRIG Zone to your hand.@@until the end of your opponent's next end phase. At the beginning of your opponent's next end phase, add the card to your hand. \n" +
                "@A $T1 #C #C: Draw a card." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "Aya//Memoria, Medium Trap");
        setDescription("en_fan",
                "@A $T1 %B0: Put 1 card from your hand onto the LRIG zone face down. Until the end of your opponent's next turn, you gain:" +
                "@>@A -M -A %B0: Add the card put onto the LRIG zone this way to your hand.@@" +
                "At the end of your opponent's next turn, add the card put onto the LRIG zone this way to your hand.\n" +
                "@A $T1 #C #C: Draw 1 card." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );

		setName("zh_simplified", "中罠 亚弥//回忆");
        setDescription("zh_simplified", 
                "@A $T1 %B0:把你的手牌1张里向放置到分身区。直到下一个对战对手的回合结束时为止，你得到\n" +
                "@>@A 主要阶段攻击阶段%B0:这个方法放置的牌从分身区加入手牌。@@\n" +
                "。下一个对战对手的回合结束时，那张牌加入手牌。\n" +
                "@A $T1 #C #C:抽1张牌。" +
                "~#对战对手的精灵1只作为对象，将其#D并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new CoinCost(2), this::onActionEff2);
            act2.setUseLimit(UseLimit.TURN, 1);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onActionEff1()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.ZONE).own().fromHand()).get();
            
            if(putOnZone(cardIndex, CardLocation.LRIG, CardUnderType.ZONE_GENERIC))
            {
                ActionAbility attachedAct = new ActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), () -> onActionEff1AddToHand(cardIndex));
                attachedAct.setActiveUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
                attachedAct.setUseLimit(UseLimit.TURN, 1);
                attachPlayerAbility(getOwner(), attachedAct, ChronoDuration.nextTurnEnd(getOpponent()));
                
                callDelayedEffect(ChronoDuration.nextTurnEnd(getOpponent()), () -> onActionEff1AddToHand(cardIndex));
            }
        }
        private void onActionEff1AddToHand(CardIndex cardIndex)
        {
            if(cardIndex.getLocation() == CardLocation.LRIG) addToHand(cardIndex);
            CardAbilities.removePlayerAbility(getAbility());
        }

        private void onActionEff2()
        {
            draw(1);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);

            draw(1);
        }
    }
}

