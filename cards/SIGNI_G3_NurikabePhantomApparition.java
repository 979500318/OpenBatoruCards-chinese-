package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardSIGNIClass;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G3_NurikabePhantomApparition extends Card {

    public SIGNI_G3_NurikabePhantomApparition()
    {
        setImageSets("WDK03-013");

        setOriginalName("幻怪　ヌリカベ");
        setAltNames("ゲンカイヌリカベ Genkai Nurikabe");
        setDescription("jp",
                "@C：あなたのルリグトラッシュにアーツが２枚以上あるかぎり、このシグニのパワーは＋7000される。"
        );

        setName("en", "Nurikabe, Phantom Apparition");
        setDescription("en",
                "@C: As long as there are 2 or more ARTS in your LRIG trash, this SIGNI gets +7000 power."
        );

		setName("zh_simplified", "幻怪 涂壁");
        setDescription("zh_simplified", 
                "@C :你的分身废弃区的必杀在2张以上时，这只精灵的力量+7000。\n"
        );

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

            registerConstantAbility(this::onConstEffCond, new PowerModifier(7000));
        }

        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().ARTS().fromTrash(DeckType.LRIG).getValidTargetsCount() >= 2 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
