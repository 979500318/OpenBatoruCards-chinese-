package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.AbilityCopyModifier;
import open.batoru.data.ability.modifiers.CardNameModifier;

public final class LRIG_K4_UrithEnmaOfDeathsBoundary extends Card {

    public LRIG_K4_UrithEnmaOfDeathsBoundary()
    {
        setImageSets("WX24-P4-023", "WX24-P4-023U");

        setOriginalName("死界の閻魔　ウリス");
        setAltNames("シカイノエンマウリス Shikai no Enma Urisu");
        setDescription("jp",
                "@C：このルリグはあなたのルリグトラッシュにあるレベル３の＜ウリス＞と同じカード名としても扱い、そのルリグの@U能力を得る。\n" +
                "@E @[エクシード４]@：あなたのトラッシュから#Gを持たないシグニを２枚まで対象とし、それらを手札に加える。\n" +
                "@A $G1 @[@|デザイア|@]@ @[シグニ１体を場からトラッシュに置く]@：&E４枚以上@0対戦相手は、手札をすべて捨てるか、自分のエナゾーンにあるすべてのカードをトラッシュに置かないかぎり、自分のすべてのシグニをトラッシュに置く。"
        );

        setName("en", "Urith, Enma of Death's Boundary");
        setDescription("en",
                "@C: This LRIG is also treated as having the same card name as a level 3 <<Urith>> in your LRIG trash, and gains that LRIG's @U abilities.\n" +
                "@E @[Exceed 4]@: Target up to 2 SIGNI without #G @[Guard]@ from your trash, and add them to your hand.\n" +
                "@A $G1 @[@|Desire|@]@ @[Put 1 SIGNI from your field into the trash]@: &E4 or more@0 Your opponent puts all of their SIGNI from the field into the trash unless they discard all cards from their hand into the trash or puts all cards from their ener zone into the trash."
        );

		setName("zh_simplified", "死界的阎魔 乌莉丝");
        setDescription("zh_simplified", 
                "@C 这只分身也视作与你的分身废弃区的等级3的<<ウリス>>相同牌名，得到那张分身的@U能力。\n" +
                "@E @[超越 4]@从你的废弃区把不持有#G的精灵2张最多作为对象，将这些加入手牌。\n" +
                "@A $G1 野望:精灵1只从场上放置到废弃区&E4张以上@0对战对手如果不把，手牌全部舍弃或，自己的能量区的全部的牌放置到废弃区，那么自己的全部的精灵放置到废弃区。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.URITH);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(4);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }


    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            TargetFilter filter = new TargetFilter().own().LRIG().withLRIGType(CardLRIGType.URITH).withLevel(3).fromTrash(DeckType.LRIG);
            registerConstantAbility(new CardNameModifier(filter), new AbilityCopyModifier(filter, ability -> ability instanceof AutoAbility));

            registerEnterAbility(new ExceedCost(4), this::onEnterEff);

            ActionAbility act = registerActionAbility(new TrashCost(new TargetFilter().SIGNI()), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Desire");
            act.setRecollect(4);
        }

        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash());
            addToHand(data);
        }

        private void onActionEff()
        {
            if(getAbility().isRecollectFulfilled())
            {
                pay(getOpponent(),
                    new TrashCost(() -> getSIGNICount(getOpponent()), new TargetFilter().SIGNI()),
                    new DiscardCost(() -> getHandCount(getOpponent())),
                    new TrashCost(() -> getEnerCount(getOpponent()), new TargetFilter(TargetHint.BURN).fromEner())
                );
            }
        }
    }
}
