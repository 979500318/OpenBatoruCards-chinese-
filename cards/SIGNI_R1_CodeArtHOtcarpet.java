package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_R1_CodeArtHOtcarpet extends Card {

    public SIGNI_R1_CodeArtHOtcarpet()
    {
        setImageSets("WXDi-P10-053");

        setOriginalName("コードアート　Ｈットカーペット");
        setAltNames("コードアートエイチットカーペット Koodo Aato Eichitto Kaapetto Hotcarpet Hot Carpet");
        setDescription("jp",
                "@E @[スペルを１枚捨てる]@：次の対戦相手のターン終了時まで、このシグニのパワーを＋10000する。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "H-Ot Carpet, Code: Art");
        setDescription("en",
                "@E @[Discard a spell]@: This SIGNI gets +10000 power until the end of your opponent's next end phase." +
                "~#Vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Code Art H Otcarpet");
        setDescription("en_fan",
                "@E @[Discard 1 spell from your hand]@: Until the end of your opponent's next turn, this SIGNI gets +10000 power." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );

		setName("zh_simplified", "必杀代号 电热垫");
        setDescription("zh_simplified", 
                "@E 魔法1张舍弃:直到下一个对战对手的回合结束时为止，这只精灵的力量+10000。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new DiscardCost(new TargetFilter().spell()), this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            gainPower(getCardIndex(), 10000, ChronoDuration.nextTurnEnd(getOpponent()));
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}
