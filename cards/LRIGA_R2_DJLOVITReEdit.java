package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.TrashCost;

public final class LRIGA_R2_DJLOVITReEdit extends Card {

    public LRIGA_R2_DJLOVITReEdit()
    {
        setImageSets("WXDi-P03-019");

        setOriginalName("DJ.LOVIT-RE-EDIT");
        setAltNames("ディージェーラビットリエディット Dii Jee Rabitto Ri Editto reedit re edit");
        setDescription("jp",
                "@E @[すべてのシグニを場からトラッシュに置き、手札とエナゾーンにあるすべてのカードをトラッシュに置く]@：対戦相手のすべてのシグニをバニッシュする。"
        );

        setName("en", "DJ LOVIT - RE: EDIT");
        setDescription("en",
                "@E @[Put all SIGNI on your field into the trash, and put all cards in your hand and Ener Zone into the trash]@: Vanish all SIGNI on your opponent's field."
        );

        setName("en_fan", "DJ.LOVIT - RE-EDIT");
        setDescription("en_fan",
                "@E @[Put all SIGNI from your field into the trash, and put all cards from your hand and ener zone into the trash]@: Banish all of your opponent's SIGNI."
        );

		setName("zh_simplified", "DJ.LOVIT-RE-EDIT");
        setDescription("zh_simplified", 
                "@E 全部的精灵从场上放置到废弃区，手牌和能量区的全部的牌放置到废弃区:对战对手的全部的精灵破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LOVIT);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.RED);
        setCost(Cost.colorless(10));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new AbilityCostList(
                new TrashCost(() -> getSIGNICount(getOwner()), new TargetFilter().own().SIGNI()),
                new DiscardCost(() -> getHandCount(getOwner())),
                new TrashCost(() -> getEnerCount(getOwner()), new TargetFilter().own().fromEner())
            ), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            banish(getSIGNIOnField(getOpponent()));
        }
    }
}
