package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_DeusThunder extends Card {

    public LRIGA_K2_DeusThunder()
    {
        setImageSets("WXDi-P05-018");

        setOriginalName("デウスサンダー");
        setAltNames("Deusu Sanda");
        setDescription("jp",
                "@E：対戦相手のデッキの一番上のカードをトラッシュに置く。その後、あなたのトラッシュからこの方法でトラッシュに置かれたシグニと同じレベルのシグニ１枚を対象とし、それを場に出す。\n" +
                "@E %K %X %X %X %X：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。"
        );

        setName("en", "Deus Thunder");
        setDescription("en",
                "@E: Put the top card of your opponent's deck into their trash. Then, put target SIGNI with the same level as the SIGNI put into the trash this way from your trash onto your field.\n" +
                "@E %K %X %X %X %X: Target SIGNI on your opponent's field gets --10000 power until end of turn."
        );
        
        setName("en_fan", "Deus Thunder");
        setDescription("en_fan",
                "@E: Put the top card of your opponent's deck into the trash. Then, target 1 SIGNI from your trash with the same level as the SIGNI put into the trash this way, and put it onto the field.\n" +
                "@E %K %X %X %X %X: Target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power."
        );

		setName("zh_simplified", "迪乌斯疾雷");
        setDescription("zh_simplified", 
                "@E :对战对手的牌组最上面的牌放置到废弃区。然后，从你的废弃区把与这个方法放置到废弃区的精灵相同等级的精灵1张作为对象，将其出场。\n" +
                "@E %K%X %X %X %X:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.DEUS);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(4)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex cardIndex = millDeck(getOpponent(), 1).get();
            
            if(CardType.isSIGNI(cardIndex.getCardReference().getType()))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(cardIndex.getIndexedInstance().getLevel().getValue()).fromTrash().playable()).get();
                putOnField(target);
            }
        }

        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -10000, ChronoDuration.turnEnd());
        }
    }
}
