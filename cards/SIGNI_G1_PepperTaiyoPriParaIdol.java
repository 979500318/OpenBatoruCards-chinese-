package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G1_PepperTaiyoPriParaIdol extends Card {

    public SIGNI_G1_PepperTaiyoPriParaIdol()
    {
        setImageSets("WXDi-P10-064");

        setOriginalName("プリパラアイドル　太陽ペッパー");
        setAltNames("プリパラアイドルタイヨウペッパー Puripara Aidoru Taiyou Peppaa");
        setDescription("jp",
                "@C：あなたのエナゾーンに＜プリパラ＞のシグニがあるかぎり、このシグニのパワーは＋5000される。"
        );

        setName("en", "Taiyo Pepper, Pripara Idol");
        setDescription("en",
                "@C: As long as there is a <<Pripara>> SIGNI in your Ener Zone, this SIGNI gets +5000 power."
        );
        
        setName("en_fan", "Pepper Taiyo, PriPara Idol");
        setDescription("en_fan",
                "@C: As long as there is a <<PirPara>> SIGNI in your ener zone, this SIGNI gets +5000 power."
        );

		setName("zh_simplified", "美妙天堂偶像 太阳佩帕");
        setDescription("zh_simplified", 
                "@C :你的能量区有<<プリパラ>>精灵时，这只精灵的力量+5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PRIPARA);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.PRIPARA).fromEner().getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
