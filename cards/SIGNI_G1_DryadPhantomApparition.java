package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G1_DryadPhantomApparition extends Card {

    public SIGNI_G1_DryadPhantomApparition()
    {
        setImageSets("WXK01-098");

        setOriginalName("幻怪　ドライアド");
        setAltNames("ゲンカイドライアド Genkai Doraiado");
        setDescription("jp",
                "@C：あなたのルリグトラッシュにアーツがあるかぎり、このシグニのパワーは＋5000される。"
        );

        setName("en", "Dryad, Phantom Apparition");
        setDescription("en",
                "@C: As long as there is an ARTS in your LRIG trash, this SIGNI gets +5000 power."
        );

		setName("zh_simplified", "幻怪 得律阿德斯");
        setDescription("zh_simplified", 
                "@C :你的分身废弃区有必杀时，这只精灵的力量+5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().ARTS().fromTrash(DeckType.LRIG).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
