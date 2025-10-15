package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_CarnivalMemoriaWickedGeneralPrincess extends Card {

    public SIGNI_K3_CarnivalMemoriaWickedGeneralPrincess()
    {
        setImageSets("WXDi-P11-048", "WXDi-P11-048P");

        setOriginalName("凶将姫　カーニバル//メモリア");
        setAltNames("キョウショウキカーニバルメモリア Kyoushouki Kaanibaru Memoria");
        setDescription("jp",
                "@U $T1：このシグニがアタックしたとき、あなたのトラッシュに黒のカードが１０枚以上あり対戦相手のエナゾーンにカードが２枚以上ある場合、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。\n" +
                "@U $T1：このシグニがアタックしたとき、あなたのトラッシュに黒のカードが２０枚以上ある場合、対戦相手は手札を１枚捨てる。\n" +
                "@E %K %X %X：対戦相手のシグニ１体を対象とし、それをバニッシュする。あなたのデッキの上からそれのレベルと同じ枚数のカードをトラッシュに置く。"
        );

        setName("en", "Carnival//Memoria, Doomed Queen");
        setDescription("en",
                "@U $T1: When this SIGNI attacks, if there are ten or more black cards in your trash and there are two or more cards in your opponent's Ener Zone, your opponent chooses a card from their Ener Zone and puts it into their trash.\n" +
                "@U $T1: When this SIGNI attacks, if there are twenty or more black cards in your trash, your opponent discards a card.\n" +
                "@E %K %X %X: Vanish target SIGNI on your opponent's field. Put a number of cards from the top of your deck equal to the targeted SIGNI's level into your trash."
        );
        
        setName("en_fan", "Carnival//Memoria, Wicked General Princess");
        setDescription("en_fan",
                "@U $T1: When this SIGNI attacks, if there are 10 or more black cards in your trash and there are 2 or more cards in your opponent's ener zone, your opponent chooses 1 card from their ener zone and puts it into the trash.\n" +
                "@U $T1: When this SIGNI attacks, if there are 20 or more black cards in your trash, your opponent discards 1 card from their hand.\n" +
                "@E %K %X %X: Target 1 of your opponent's SIGNI, and banish it. Put a number of cards equal to its level from the top of your deck into the trash."
        );

		setName("zh_simplified", "凶将姬 嘉年华//回忆");
        setDescription("zh_simplified", 
                "@U $T1 :当这只精灵攻击时，你的废弃区的黑色的牌在10张以上且对战对手的能量区的牌在2张以上的场合，对战对手从自己的能量区选1张牌放置到废弃区。\n" +
                "@U $T1 :当这只精灵攻击时，你的废弃区的黑色的牌在20张以上的场合，对战对手把手牌1张舍弃。\n" +
                "@E %K%X %X:对战对手的精灵1只作为对象，将其破坏。从你的牌组上面把与其的等级相同张数的牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);
            auto1.setUseLimit(UseLimit.TURN, 1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
            auto2.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(2)), this::onEnterEff);
        }

        private void onAutoEff1()
        {
            if(new TargetFilter().own().withColor(CardColor.BLACK).fromTrash().getValidTargetsCount() >= 10 &&
               getEnerCount(getOpponent()) >= 2)
            {
                CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                trash(cardIndex);
            }
        }

        private void onAutoEff2()
        {
            if(new TargetFilter().own().withColor(CardColor.BLACK).fromTrash().getValidTargetsCount() >= 20)
            {
                discard(getOpponent(), 1);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null)
            {
                banish(target);
                
                millDeck(target.getIndexedInstance().getLevelByRef());
            }
        }
    }
}
