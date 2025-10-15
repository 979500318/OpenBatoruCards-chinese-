package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R1_CarnivalDissonaNaturalStar extends Card {

    public SIGNI_R1_CarnivalDissonaNaturalStar()
    {
        setImageSets("WXDi-P13-062", "SPDi10-12");
        setLinkedImageSets("WXDi-P07-TK01-A");

        setOriginalName("羅星　カーニバル//ディソナ");
        setAltNames("ラセイカーニバルディソナ Rasei Kaanibaru Disona");
        setDescription("jp",
                "@U：あなたのターン終了時、クラフトの《サーバント　ＺＥＲＯ》１つを場に出す。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Carnival//Dissona, Natural Planet");
        setDescription("en",
                "@U: At the end of your turn, put a \"Servant ZERO\" Craft onto your field.\n" +
                "~#Vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Carnival//Dissona, Natural Star");
        setDescription("en_fan",
                "@U: At the end of your turn, put 1 \"Servant ZERO\" craft onto the field." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );

		setName("zh_simplified", "罗星 嘉年华//失调");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，衍生的《サーバント　ＺＥＲＯ》1只出场。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.DISSONA | CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().zone().playable().getValidTargetsCount() > 0)
            {
                CardIndex cardIndex = craft(getLinkedImageSets().get(0));
                
                if(!putOnField(cardIndex))
                {
                    exclude(cardIndex);
                }
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}

