package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R2_KomainuDissonaPhantomBeast extends Card {

    public SIGNI_R2_KomainuDissonaPhantomBeast()
    {
        setImageSets("WXDi-P12-067");

        setOriginalName("幻獣　コマイヌ//ディソナ");
        setAltNames("ゲンジュウコマイヌディソナ Genjuu Komainu Disona");
        setDescription("jp",
                "@U：このシグニがバトルによって対戦相手のシグニ１体をバニッシュしたとき、対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。このシグニのパワーが10000以上の場合、代わりに対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Komainu//Dissona, Phantom Beast");
        setDescription("en",
                "@U: Whenever this SIGNI vanishes a SIGNI on your opponent's field through battle, vanish target SIGNI on your opponent's field with power 3000 or less. If this SIGNI's power is 10000 or more, instead vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Komainu//Dissona, Phantom Beast");
        setDescription("en_fan",
                "@U: Whenever this SIGNI banished 1 of your opponent's SIGNI in battle, target 1 of your opponent's SIGNI with power 3000 or less, and banish it. If this SIGNI's power is 10000 or more, instead target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );

		setName("zh_simplified", "幻兽 狛犬//失调");
        setDescription("zh_simplified", 
                "@U :当这只精灵因为战斗把对战对手的精灵1只破坏时，对战对手的力量3000以下的精灵1只作为对象，将其破坏。这只精灵的力量在10000以上的场合，作为替代，对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceAbility() == null && !isOwnCard(caller) && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter().OP().SIGNI().withPower(0, getPower().getValue() < 10000 ? 3000 : 8000)).get();
            banish(target);
        }
    }
}
