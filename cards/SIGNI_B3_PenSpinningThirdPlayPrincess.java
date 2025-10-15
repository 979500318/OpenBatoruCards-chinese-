package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_B3_PenSpinningThirdPlayPrincess extends Card {

    public SIGNI_B3_PenSpinningThirdPlayPrincess()
    {
        setImageSets("WX24-P2-052");
        setLinkedImageSets("WX24-P2-022");

        setOriginalName("参ノ遊姫　ボールペンマワシ");
        setAltNames("サンノユウキボールペンマワシ San no Yuuki Boorupen Mawashi");
        setDescription("jp",
                "@U $T1：アタックフェイズの間、あなたの＜遊具＞のシグニ１体が場を離れたとき、あなたの手札からレベル２以下の＜遊具＞のシグニ１枚をダウン状態で場に出してもよい。そのシグニの@E能力は発動しない。\n" +
                "@U：このシグニがアタックしたとき、あなたの場に《あきら☆らっきー》がいる場合、対戦相手のシグニ１体を対象とし、あなたの他の＜遊具＞のシグニ１体を場からデッキの一番下に置いてもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );

        setName("en", "Pen Spinning, Third Play Princess");
        setDescription("en",
                "@U $T1: During the attack phase, when 1 of your <<Playground Equipment>> SIGNI leaves the field, you may put 1 level 2 or lower <<Playground Equipment>> SIGNI from your hand onto the field downed. That SIGNI's @E abilities don't activate.\n" +
                "@U: Whenever this SIGNI attacks, if your LRIG is \"Akira☆Lucky\", target 1 of your opponent's SIGNI, and you may put 1 of your other <<Playground Equipment>> SIGNI from your field on the bottom of your deck. If you do, until end of turn, it gets --12000 power."
        );

		setName("zh_simplified", "叁之游姬 转笔");
        setDescription("zh_simplified", 
                "@U $T1 攻击阶段期间，当你的<<遊具>>精灵1只离场时，可以从你的手牌把等级2以下的<<遊具>>精灵1张以#D状态出场。那只精灵的@E能力不能发动。\n" +
                "@U :当这只精灵攻击时，你的场上有《あきら☆らっきー》的场合，对战对手的精灵1只作为对象，可以把你的其他的<<遊具>>精灵1只从场上放置到牌组最下面。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.MOVE, this::onAutoEff1);
            auto1.setCondition(this::onAutoEffCond1);
            auto1.setUseLimit(UseLimit.TURN, 1);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }

        private ConditionState onAutoEffCond1(CardIndex caller)
        {
            return GamePhase.isAttackPhase(getCurrentPhase()) && isOwnCard(caller) &&
                    CardLocation.isSIGNI(caller.getLocation()) && caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.PLAYGROUND_EQUIPMENT) &&
                    !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).withLevel(0,2).fromHand().playable()).get();
            putOnField(cardIndex, Enter.DOWNED | Enter.DONT_ACTIVATE);
        }

        private void onAutoEff2()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("あきら☆らっきー"))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                if(target != null)
                {
                    CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.BOTTOM).own().SIGNI().withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).except(getCardIndex())).get();
                    if(returnToDeck(cardIndex, DeckPosition.BOTTOM))
                    {
                        gainPower(target, -12000, ChronoDuration.turnEnd());
                    }
                }
            }
        }
    }
}
