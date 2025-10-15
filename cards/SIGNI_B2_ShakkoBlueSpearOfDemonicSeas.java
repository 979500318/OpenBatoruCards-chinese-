package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardSIGNIClass;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B2_ShakkoBlueSpearOfDemonicSeas extends Card {

    public SIGNI_B2_ShakkoBlueSpearOfDemonicSeas()
    {
        setImageSets("WDK02-015");

        setOriginalName("魔海の青槍　シャッコー");
        setAltNames("マカイノセイソウシャッコー Makai no Seisou Shakkoo");
        setDescription("jp",
                "@C：あなたの手札が１枚以下であるかぎり、このシグニのパワーは＋3000される。"
        );

        setName("en", "Shakko, Blue Spear of Demonic Seas");
        setDescription("en",
                "@C: As long as there are 1 or less cards in your hand, this SIGNI gets +3000 power."
        );

		setName("zh_simplified", "魔海的青枪 夏克");
        setDescription("zh_simplified", 
                "@C :你的手牌在1张以下时，这只精灵的力量+3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000));
        }

        private ConditionState onConstEffCond()
        {
            return getHandCount(getOwner()) <= 1 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
