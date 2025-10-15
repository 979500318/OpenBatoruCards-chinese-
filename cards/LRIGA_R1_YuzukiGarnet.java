package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_R1_YuzukiGarnet extends Card {

    public LRIGA_R1_YuzukiGarnet()
    {
        setImageSets("WXDi-P10-023");

        setOriginalName("遊月・ガーネット");
        setAltNames("ユヅキガーネット Tuzuki Gaanetto");
        setDescription("jp",
                "@E：対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E：対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード１枚を対象とし、それをトラッシュに置く。"
        );

        setName("en", "Yuzuki Garnet");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field with power 5000 or less.\n" +
                "@E: Put target card from your opponent's Ener Zone that does not share a color with your opponent's Center LRIG into their trash."
        );
        
        setName("en_fan", "Yuzuki Garnet");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 5000 or less, and banish it.\n" +
                "@E: Target 1 card from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and put it into the trash."
        );

		setName("zh_simplified", "游月·石榴石");
        setDescription("zh_simplified", 
                "@E :对战对手的力量5000以下的精灵1只作为对象，将其破坏。\n" +
                "@E :从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，将其放置到废弃区。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.YUZUKI);
        setColor(CardColor.RED);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
            banish(target);
        }
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor())).fromEner()).get();
            trash(target);
        }
    }
}
