package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_W3_MagellanNaturalStarPrincess extends Card {

    public SIGNI_W3_MagellanNaturalStarPrincess()
    {
        setImageSets("WX24-P4-043");

        setOriginalName("羅星姫　マゼラン");
        setAltNames("ラセイキマゼラン Raseiki Mazeran");
        setDescription("jp",
                "@U $TP $T1：あなたのシグニ１体が場を離れたとき、あなたのデッキの一番上を公開する。その後、以下の２つから１つを選ぶ。\n" +
                "$$1この方法で公開されたカードがレベル１のシグニの場合、そのシグニをダウン状態で場に出してもよい。そのシグニの@E能力は発動しない。\n" +
                "$$2カードを１枚引く。\n" +
                "@E %W：あなたのデッキの上からカードを３枚見る。その中からカードを１枚まで手札に加え、残りを好きな順番でデッキの一番上に戻す。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをトラッシュに置く。"
        );

        setName("en", "Magellan, Natural Star Princess");
        setDescription("en",
                "@U $TP $T1: When 1 of your SIGNI leaves the field, reveal the top card of your deck. Then, @[@|choose 1 of the following:|@]@\n" +
                "$$1 If it is a level 1 SIGNI, you may put that SIGNI onto the field downed. Its @E abilities don't activate.\n" +
                "$$2 Draw 1 card.\n" +
                "@E %W: Look at the top 3 cards of your deck. Add up to 1 card from among them to your hand, and put the rest on the top of your deck in any order." +
                "~#Target 1 of your opponent's upped SIGNI, and put into the trash."
        );

		setName("zh_simplified", "罗星姬 麦哲伦星系");
        setDescription("zh_simplified", 
                "@U $TP $T1 :当你的精灵1只离场时，你的牌组最上面公开。然后，从以下的2种选1种。\n" +
                "$$1 这个方法公开的牌是等级1的精灵的场合，可以把那张精灵以横置状态出场。那只精灵的@E能力不能发动。\n" +
                "$$2 抽1张牌。\n" +
                "@E %W:从你的牌组上面看3张牌。从中把牌1张最多加入手牌，剩下的任意顺序返回牌组最上面。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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

            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnTurn() && isOwnCard(caller) && caller.isSIGNIOnField() &&
                   !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                int mode = playerChoiceMode();
                if((mode == 1 && (cardIndex.getIndexedInstance().getLevelByRef() != 1 || !putOnField(cardIndex, Enter.DONT_ACTIVATE | Enter.DOWNED))) ||
                   (mode == 2 && draw(1).get() == null))
                {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            }
        }
        
        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().upped()).get();
            trash(target);
        }
    }
}
