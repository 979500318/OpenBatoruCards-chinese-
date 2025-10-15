package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R1_CodeArtLalaruFessone extends Card {

    public SIGNI_R1_CodeArtLalaruFessone()
    {
        setImageSets("WXDi-P14-055");

        setOriginalName("コードアート　ララ・ルー//フェゾーネ");
        setAltNames("コードアートララルーフェゾーネ Koodo Aato Rararuu Fezoone");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニが赤の場合、対戦相手のパワー5000以下のシグニ１体を対象とし、手札から赤のカードを１枚捨ててもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Lalaru//Fesonne, Code: Art");
        setDescription("en",
                "@U: At the beginning of your attack phase, if all the SIGNI on your field are red, you may discard a red card. If you do, vanish target SIGNI on your opponent's field with power 5000 or less."
        );
        
        setName("en_fan", "Code Art Lalaru//Fessone");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if all of your SIGNI are red, target 1 of your opponent's SIGNI with power 5000 or less, and you may discard 1 red card from your hand. If you do, banish it."
        );

		setName("zh_simplified", "必杀代号 啦啦·噜//音乐节");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的全部的精灵是红色的场合，对战对手的力量5000以下的精灵1只作为对象，可以从手牌把红色的牌1张舍弃。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            if(getSIGNICount(getOwner()) > 0 && new TargetFilter().own().SIGNI().not(new TargetFilter().withColor(CardColor.RED)).getValidTargetsCount() == 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
                
                if(target != null && discard(0,1, new TargetFilter().withColor(CardColor.RED)).get() != null)
                {
                    banish(target);
                }
            }
        }
    }
}
