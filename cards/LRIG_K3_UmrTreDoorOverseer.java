package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class LRIG_K3_UmrTreDoorOverseer extends Card {

    public LRIG_K3_UmrTreDoorOverseer()
    {
        setImageSets("WXDi-P16-013", "WXDi-P16-013U");

        setOriginalName("扉の俯瞰者　ウムル＝トレ");
        setAltNames("トビラノフカンシャウムルトレ Tobira no Fukansha Umuru Tore");
        setDescription("jp",
                "@E：各プレイヤーは自分のデッキの上からカードを３枚トラッシュに置く。\n" +
                "@A $T1 %K0:対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをあなたのトラッシュにある黒のカード１０枚につき－5000する。\n" +
                "@A $G1 %K0：あなたのトラッシュから黒のシグニを２枚まで対象とし、それらを場に出す。"
        );

        setName("en", "Umr =Tre=, Gate Overseer");
        setDescription("en",
                "@E: Each player puts the top three cards of their deck into their trash.\n@A $T1 %K0: Target SIGNI on your opponent's field gets --5000 power for every ten black cards in your trash until end of turn.\n@A $G1 %K0: Put up to two target black SIGNI from your trash onto your field."
        );
        
        setName("en_fan", "Umr-Tre, Door Overseer");
        setDescription("en_fan",
                "@E: Each player puts the top 3 cards of their deck into the trash.\n" +
                "@A $T1 %K0: Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power for every 10 black cards in your trash.\n" +
                "@A $G1 %K0: Target up to 2 black SIGNI from your trash, and put them onto the field."
        );

		setName("zh_simplified", "扉的俯瞰者 乌姆尔=TRE");
        setDescription("zh_simplified", 
                "@E :各玩家从自己的牌组上面把3张牌放置到废弃区。\n" +
                "@A $T1 %K0:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据你的废弃区的黑色的牌的数量，每有10张就-5000。\n" +
                "@A $G1 %K0:从你的废弃区把黑色的精灵2张最多作为对象，将这些出场。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.UMR);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private void onEnterEff()
        {
            millDeck(getOwner(), 3);
            millDeck(getOpponent(), 3);
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null) gainPower(target, -5000 * (new TargetFilter().own().withColor(CardColor.BLACK).fromTrash().getValidTargetsCount()/10), ChronoDuration.turnEnd());
        }
        
        private void onActionEff2()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(CardColor.BLACK).fromTrash().playable());
            putOnField(data);
        }
    }
}
