package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W2_FlamberMediumEquipment extends Card {

    public SIGNI_W2_FlamberMediumEquipment()
    {
        setImageSets("WX24-D1-15");

        setOriginalName("中装　フランベル");
        setAltNames("チュウソウフランベル Chuusou Furanberu");
        setDescription("jp",
                "@C：あなたの白のシグニのパワーを＋2000する。"
        );

        setName("en", "Flamber, Medium Equipment");
        setDescription("en",
                "@C: All of your white SIGNI get +2000 power."
        );

		setName("zh_simplified", "中装 焰形剑");
        setDescription("zh_simplified", 
                "@C :你的白色的精灵的力量+2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new TargetFilter().own().SIGNI().withColor(CardColor.WHITE), new PowerModifier(2000));
        }
    }
}
