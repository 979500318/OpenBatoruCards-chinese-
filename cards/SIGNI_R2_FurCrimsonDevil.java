package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_R2_FurCrimsonDevil extends Card {

    public SIGNI_R2_FurCrimsonDevil()
    {
        setImageSets("WX24-P1-063");

        setOriginalName("紅魔　フュール");
        setAltNames("コウマフュール Kouma Fyuuru");
        setDescription("jp",
                "@E @[他の＜悪魔＞のシグニ１体を場からトラッシュに置く]@：対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Für, Crimson Devil");
        setDescription("en",
                "@E @[Put 1 of your other <<Devil>> SIGNI into the trash]@: Target 1 of your opponent's SIGNI with power 3000 or less, and banish it." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );

		setName("zh_simplified", "红魔 佛尔");
        setDescription("zh_simplified", 
                "@E 其他的<<悪魔>>精灵1只从场上放置到废弃区:对战对手的力量3000以下的精灵1只作为对象，将其破坏。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new TrashCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.DEVIL).except(cardId)), this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,3000)).get();
            banish(target);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}
