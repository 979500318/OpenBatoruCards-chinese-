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

public final class SIGNI_G3_CatmiNaturalStarPrincess extends Card {

    public SIGNI_G3_CatmiNaturalStarPrincess()
    {
        setImageSets("WX24-P1-047");

        setOriginalName("羅星姫　キャトミ");
        setAltNames("ラセイキキャトミ Raseiki Kyatomi");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のパワー10000以上のシグニ１体を対象とし、あなたのエナゾーンからレベル１のシグニ２枚をトラッシュに置いてもよい。そうした場合、それをバニッシュする。\n" +
                "@E：あなたのデッキの一番上を公開する。そのカードがレベル１のシグニの場合、【エナチャージ１】をする。" +
                "~#：どちらか１つを選ぶ。\n$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n$$2【エナチャージ１】"
        );

        setName("en", "Catmi, Natural Star Princess");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with power 10000 or more, and you may put 2 level 1 SIGNI from your ener zone into the trash. If you do, banish it.\n" +
                "@E: Reveal the top card of your deck. If that card is a level 1 SIGNI, [[Ener Charge 1]]." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "罗星姬 屠牛事件");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的力量10000以上的精灵1只作为对象，可以从你的能量区把等级1的精灵2张放置到废弃区。这样做的场合，将其破坏。\n" +
                "@E :你的牌组最上面公开。那张牌是等级1的精灵的场合，[[能量填充1]]。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0)).get();
            
            if(target != null)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().SIGNI().withLevel(1).fromEner());
                if(trash(data) == 2)
                {
                    banish(target);
                }
            }
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex == null ||
               !CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) || cardIndex.getIndexedInstance().getLevelByRef() != 1 ||
               enerCharge(1).get() == null)
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                enerCharge(1);
            }
        }
    }
}
