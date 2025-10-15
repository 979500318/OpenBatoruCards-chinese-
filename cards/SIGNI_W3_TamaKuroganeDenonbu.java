package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_W3_TamaKuroganeDenonbu extends Card {

    public SIGNI_W3_TamaKuroganeDenonbu()
    {
        setImageSets("WXDi-P14-079", "WXDi-P14-079P");
        setLinkedImageSets("WXDi-P14-078", "WXDi-P14-080");

        setOriginalName("電音部　黒鉄たま");
        setAltNames("デンオンブクロガネタマ Denonbu Kurogane Tama");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、あなたのエナゾーンから＜電音部＞のシグニ３枚をトラッシュに置いてもよい。そうした場合、それを手札に戻す。\n" +
                "@E @[手札を１枚捨てる]@：あなたのデッキの上からカードを５枚見る。その中から《電音部　白金煌》か《電音部　灰島銀華》を１枚まで公開し手札に加え、＜電音部＞のシグニを１枚までエナゾーンに置き、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "DEN-ON-BU Tama Kurogane");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may put three <<DEN-ON-BU>> SIGNI from your Ener Zone into your trash. If you do, return target SIGNI on your opponent's field to its owner's hand.\n@E @[Discard a card]@: Look at the top five cards of your deck. Reveal up to one \"DEN-ON-BU Aki Shirokane\" or \"DEN-ON-BU Ginka Haijima\" from among them and add it to your hand, put up to one <<DEN-ON-BU>> SIGNI into your Ener Zone, and put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Tama Kurogane, Denonbu");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may put 3 <<Denonbu>> SIGNI from your ener zone into the trash. If you do, return it to their hand.\n" +
                "@E @[Discard 1 card from your hand]@: Look at the top 5 cards of your deck. Reveal up to 1 \"Aki Shirogane, Denonbu\" or \"Ginka Haijima, Denonbu\" from among them, and add them to your hand, put up to 1 <<Denonbu>> SIGNI from among them into the ener zone, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "电音部 黑铁多麻");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，可以从你的能量区把<<電音部>>精灵3张放置到废弃区。这样做的场合，将其返回手牌。\n" +
                "@E 手牌1张舍弃:从你的牌组上面看5张牌。从中把《電音部　白金煌》或《電音部　灰島銀華》1张最多公开加入手牌，<<電音部>>精灵1张最多放置到能量区，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DENONBU);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(new DiscardCost(1), this::onEnterEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            
            if(target != null)
            {
                DataTable<CardIndex> data = playerTargetCard(0,3, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.DENONBU).fromEner());
                
                if(trash(data) == 3)
                {
                    addToHand(target);
                }
            }
        }
        
        private void onEnterEff()
        {
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.DENONBU).withName("電音部　白金煌", "電音部　灰島銀華").fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.ENER).own().SIGNI().withClass(CardSIGNIClass.DENONBU).fromLooked()).get();
            putInEner(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
