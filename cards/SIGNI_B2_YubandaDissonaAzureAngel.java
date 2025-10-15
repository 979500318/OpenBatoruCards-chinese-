package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B2_YubandaDissonaAzureAngel extends Card {

    public SIGNI_B2_YubandaDissonaAzureAngel()
    {
        setImageSets("WXDi-P13-073");

        setOriginalName("蒼天　ユバンダ//ディソナ");
        setAltNames("ソウテンユバンダディソナ Souten Yubanda Disona");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニが#Sの場合、カードを１枚引く。"
        );

        setName("en", "Yubanda//Dissona, Azure Angel");
        setDescription("en",
                "@U: At the beginning of your attack phase, if all the SIGNI on your field are #S, draw a card."
        );
        
        setName("en_fan", "Yubanda//Dissona, Azure Angel");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if all of your SIGNI are #S @[Dissona]@ SIGNI, draw 1 card."
        );

		setName("zh_simplified", "苍天 岩和田//失调");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，你的场上的全部的精灵是#S的场合，抽1张牌。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().not(new TargetFilter().dissona()).getValidTargetsCount() == 0)
            {
                draw(1);
            }
        }
    }
}
