package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_G3_KamaitachiPhantomApparition extends Card {

    public SIGNI_G3_KamaitachiPhantomApparition()
    {
        setImageSets("SPK01-03");

        setOriginalName("幻怪　カマイタチ");
        setAltNames("ゲンカイカマイタチ Genkai Kamaitachi");
        setDescription("jp",
                "@E %G @[ルリグデッキからアーツ１枚をルリグトラッシュに置く]@：対戦相手のレベル３以上のシグニ１体を対象とし、それをエナゾーンに置く。"
        );

        setName("en", "Kamaitachi, Phantom Apparition");
        setDescription("en",
                "@E %G @[Put 1 ARTS from your LRIG deck into the LRIG trash]@: Target 1 of your opponent's level 3 or higher SIGNI, and put it into the ener zone."
        );

		setName("zh_simplified", "幻怪 镰鼬");
        setDescription("zh_simplified", 
                "@E %G从你的分身牌组把必杀1张放置到分身废弃区:对战对手的等级3以上的精灵1只作为对象，将其放置到能量区。\n"
        );

        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
        setLevel(3);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.GREEN, 1)),
                new TrashCost(new TargetFilter().ARTS().fromLocation(CardLocation.DECK_LRIG))
            ), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).OP().SIGNI().withLevel(3,0)).get();
            putInEner(target);
        }
    }
}
