package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.AbilityGain;

public final class SIGNI_W1_CodeArtLIONFessone extends Card {

    public SIGNI_W1_CodeArtLIONFessone()
    {
        setImageSets("WXDi-P14-051");

        setOriginalName("コードアート　LION//フェゾーネ");
        setAltNames("コードアートリオンフェゾーネ Koodo Aato Rion Fezoone");
        setDescription("jp",
                "@E：ターン終了時まで、対戦相手のレベル２以下のすべてのシグニは能力を失う。"
        );

        setName("en", "LION//Fesonne, Code: Art");
        setDescription("en",
                "@E: All level two or less SIGNI on your opponent's field lose their abilities until end of turn. "
        );
        
        setName("en_fan", "Code Art LION//Fessone");
        setDescription("en_fan",
                "@E: Until end of turn, all of your opponent's level 2 or lower SIGNI lose their abilities."
        );

		setName("zh_simplified", "必杀代号 LION//音乐节");
        setDescription("zh_simplified", 
                "@E :直到回合结束时为止，对战对手的等级2以下的全部的精灵的能力失去。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            disableAllAbilities(new TargetFilter().OP().SIGNI().withLevel(0,2).getExportedData(), AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
    }
}
