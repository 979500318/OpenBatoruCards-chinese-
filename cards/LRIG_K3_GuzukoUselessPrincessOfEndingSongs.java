package open.batoru.data.cards;

import open.batoru.core.Deck;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_K3_GuzukoUselessPrincessOfEndingSongs extends Card {

    public LRIG_K3_GuzukoUselessPrincessOfEndingSongs()
    {
        setImageSets("WXDi-P09-010", "WXDi-P09-010U");

        setOriginalName("絶歌の駄姫　グズ子");
        setAltNames("ゼッカのダキグズコ Zekka no Daki Guzuko");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを５枚見る。その中からカード１枚をトラッシュに置き、残りを好きな順番でデッキの一番下に置く。\n" +
                "@A $T1 %K %X：あなたのトラッシュからシグニ１枚を対象とし、それを場に出す。\n" +
                "@A $G1 %K0：このターン、あなたのシグニ１体が効果によって場に出たとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Guzuko, Worthless Queen of Triumph");
        setDescription("en",
                "@E: Look at the top five cards of your deck. Put a card from among them into your trash and put the rest on the bottom of your deck in any order.\n" +
                "@A $T1 %K %X: Put target SIGNI from your trash onto your field.\n" +
                "@A $G1 %K0: Whenever a SIGNI enters your field by an effect this turn, target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "Guzuko, Useless Princess of Ending Songs");
        setDescription("en_fan",
                "@E: Look at the top 5 cards of your deck. Put 1 card from among them into the trash, and put the rest on the bottom of your deck in any order.\n" +
                "@A $T1 %K %X: Target 1 SIGNI from your trash, and put it onto the field.\n" +
                "@A $G1 %K0: This turn, whenever 1 of your SIGNI enters the field by an effect, target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "绝歌的的驮姬 迟钝子");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看5张牌。从中把1张牌放置到废弃区，剩下的任意顺序放置到牌组最下面。\n" +
                "@A $T1 %K%X:从你的废弃区把精灵1张作为对象，将其出场。\n" +
                "@A $G1 %K0:这个回合，当你的精灵1只因为效果出场时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.GUZUKO);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);
        setCoins(+2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }
        
        private void onEnterEff()
        {
            look(5);
            
            if(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TRASH).own().fromLooked()).get();
                trash(cardIndex);
                
                while(getLookedCount() > 0)
                {
                    cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                    returnToDeck(cardIndex, Deck.DeckPosition.BOTTOM);
                }
            }
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable()).get();
            putOnField(target);
        }
        
        private void onActionEff2()
        {
            AutoAbility attachedAuto = new AutoAbility(GameConst.GameEventId.ENTER, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            
            attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) &&
                   getEvent().getSourceAbility() != null ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}
