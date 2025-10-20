package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_W3_CodeHeartLMusicalNoteAmp extends Card {

    public SIGNI_W3_CodeHeartLMusicalNoteAmp()
    {
        setImageSets("SPDi43-17");
        setLinkedImageSets("SPDi43-11");

        setOriginalName("コードハート　Lオンプアンプ");
        setAltNames("コードハートエルオンプアンプ Koodo Haato Eru Onpuanpu");
        setDescription("jp",
                "@U $T1：あなたの《MC.LION 3rdVerse-ULT》がアタックしたとき、あなたのデッキの上からカードを５枚見る。その中からスペル１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。\n" +
                "@U：このシグニがアタックしたとき、このターンにあなたがスペルを使用していた場合、あなたのルリグ１体を対象とし、%Wを支払ってもよい。そうした場合、それをアップする。"
        );

        setName("en", "Code Heart L Musical Note Amp");
        setDescription("en",
                "@U $T1: When your \"MC.LION 3rd Verse-ULT\" attacks, look at the top 5 cards of your deck. Reveal 1 spell from among them, add it to your hand, and put the rest on the bottom of your deck in any order.\n" +
                "@U: Whenever this SIGNI attacks, if you used a spell this turn, target 1 of your LRIG, and you may pay %W. If you do, up it."
        );

		setName("zh_simplified", "爱心代号 L扩音");
        setDescription("zh_simplified", 
                "@U $T1 当你的《MC.LION:3rdVerse-ULT》横置时，从你的牌组上面看5张牌。从中把魔法1张公开加入手牌，剩下的任意顺序放置到牌组最下面。\n" +
                "@U :当这只精灵攻击时，这个回合你把魔法使用过的场合，你的分身1只作为对象，可以支付%W。这样做的场合，将其竖直。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }

        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.getIndexedInstance().getName().getValue().contains("MC.LION 3rdVerse-ULT") ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().spell().fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private void onAutoEff2()
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_SPELL && isOwnCard(event.getCaller())) > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.UP).own().anyLRIG()).get();
                
                if(target != null && payEner(Cost.color(CardColor.WHITE, 1)))
                {
                    up(target);
                }
            }
        }
    }
}
