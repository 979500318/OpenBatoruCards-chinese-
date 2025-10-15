package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_R1_PhalegCrimsonAngel extends Card {

    public SIGNI_R1_PhalegCrimsonAngel()
    {
        setImageSets("WX25-P1-074");

        setOriginalName("紅天　ファレグ");
        setAltNames("コウテンファレグ Kouten Faregu");
        setDescription("jp",
                "@E @[手札から＜天使＞のシグニを１枚捨てる]@：対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Phaleg, Crimson Angel");
        setDescription("en",
                "@E @[Discard 1 <<Angel>> SIGNI from your hand]@: Target 1 of your opponent's SIGNI with power 5000 or less, and banish it."
        );

		setName("zh_simplified", "红天 富雷格");
        setDescription("zh_simplified", 
                "@E 从手牌把<<天使>>精灵1张舍弃:对战对手的力量5000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new DiscardCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.ANGEL)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
            banish(target);
        }
    }
}
