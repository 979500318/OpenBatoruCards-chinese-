package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_W3_MunkarunHolyAngelPrincess extends Card {

    public SIGNI_W3_MunkarunHolyAngelPrincess()
    {
        setImageSets("WX25-P1-052", "WX25-P1-052U");
        setLinkedImageSets("WX25-P1-014");

        setOriginalName("聖天姫　ムンカルン");
        setAltNames("セイテンキムンカルン Seitenki Munkarun");
        setDescription("jp",
                "@U $TP $T1：あなたの＜天使＞のシグニ１体が場を離れたとき、あなたの場に《永らえし冒険者　タウィル＝トレ》がいる場合、あなたのデッキの上からカードを３枚見る。その中からレベル２以下の＜天使＞のシグニ1枚を場に出し、残りを好きな順番でデッキの一番下に置く。このシグニの@E能力は発動しない。\n" +
                "@A #D：あなたのデッキの上からカードを３枚見る。その中から＜天使＞のシグニ１枚を場に出し、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Munkarun, Holy Angel Princess");
        setDescription("en",
                "@U $TP $T1: When 1 of your <<Angel>> SIGNI leaves the field, if your LRIG is \"Tawil-Tre, Prolonged of Life Adventurer\", look at the top 3 cards of your deck. Put 1 level 2 or lower <<Angel>> SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order. That SIGNI's @E abilities don't activate.\n" +
                "@A #D: Look at the top 3 cards of your deck. Put 1 <<Angel>> SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "圣天姬 孟凯尔");
        setDescription("zh_simplified", 
                "@U $TP $T1 :当你的<<天使>>精灵1只离场时，你的场上有《永らえし冒険者タウィル＝トレ》的场合，从你的牌组上面看3张牌。从中把等级2以下的<<天使>>精灵1张出场，剩下的任意顺序放置到牌组最下面。那只精灵的@E能力不能发动。\n" +
                "@A 横置:从你的牌组上面看3张牌。从中把<<天使>>精灵1张出场，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            registerActionAbility(new DownCost(), this::onActionEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnTurn() && isOwnCard(caller) && caller.isSIGNIOnField() &&
                   caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.ANGEL) &&
                   !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("永らえし冒険者　タウィル＝トレ"))
            {
                look(3);
                
                DataTable<CardIndex> data = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(0,2).withClass(CardSIGNIClass.ANGEL).fromLooked().playable());
                putOnField(data, Enter.DONT_ACTIVATE);
                
                while(getLookedCount() > 0)
                {
                    CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                    returnToDeck(cardIndex, DeckPosition.BOTTOM);
                }
            }
        }
        
        private void onActionEff()
        {
            look(3);
            
            DataTable<CardIndex> data = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromLooked().playable());
            putOnField(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
