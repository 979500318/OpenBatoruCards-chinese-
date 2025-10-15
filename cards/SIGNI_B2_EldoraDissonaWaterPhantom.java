package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_B2_EldoraDissonaWaterPhantom extends Card {

    public SIGNI_B2_EldoraDissonaWaterPhantom()
    {
        setImageSets("WXDi-P16-075");

        setOriginalName("幻水　エルドラ//ディソナ");
        setAltNames("ゲンスイエルドラディソナ Gensui Erudora Disona");
        setDescription("jp",
                "@A %B #D：対戦相手の凍結状態のパワー3000以下のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );

        setName("en", "Eldora//Dissona, Aquatic Beast");
        setDescription("en",
                "@A %B #D: Put target frozen SIGNI on your opponent's field with power 3000 or less on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Eldora//Dissona, Water Phantom");
        setDescription("en_fan",
                "@A %B #D: Target 1 of your opponent's frozen SIGNI with power 3000 or less, and put it on the bottom of their deck."
        );

		setName("zh_simplified", "幻水 艾尔德拉//失调");
        setDescription("zh_simplified", 
                "@A %B#D:对战对手的冻结状态的力量3000以下的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.BLUE, 1)), new DownCost()), this::onActionEff);
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().withState(CardStateFlag.FROZEN).withPower(0,3000)).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }
    }
}
