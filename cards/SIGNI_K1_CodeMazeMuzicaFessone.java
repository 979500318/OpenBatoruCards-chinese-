package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

import java.util.HashSet;
import java.util.Set;

public final class SIGNI_K1_CodeMazeMuzicaFessone extends Card {

    public SIGNI_K1_CodeMazeMuzicaFessone()
    {
        setImageSets("WXDi-P14-067");

        setOriginalName("コードメイズ　ムジカ//フェゾーネ");
        setAltNames("コードメイズムジカフェゾーネ Koodo Meizu Mujika Fezoone");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－2000する。あなたの場にあるシグニがそれぞれ共通する色を持たない場合、代わりにターン終了時まで、それのパワーを－3000する。"
        );

        setName("en", "Muzica//Fesonne, Code: Maze");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may pay %X. If you do, target SIGNI on your opponent's field gets --2000 power until end of turn. If no SIGNI on your field share a color, it gets --3000 power until end of turn instead."
        );
        
        setName("en_fan", "Code Maze Muzica//Fessone");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may pay %X. If you do, until end of turn, it gets --2000 power. If none of your SIGNI share a common color, until end of turn, it gets --3000 power instead."
        );

		setName("zh_simplified", "迷宫代号  穆希卡//音乐节");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，可以支付%X。这样做的场合，直到回合结束时为止，其的力量-2000。你的场上的精灵不持有共通颜色的场合，作为替代，直到回合结束时为止，其的力量-3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                Set<CardColor> cacheColors = new HashSet<>();
                DataTable<CardIndex> data = getSIGNIOnField(getOwner());
                
                boolean match = false;
                for(int i=0;i<data.size();i++)
                {
                    CardDataColor dataColor = data.get(i).getIndexedInstance().getColor();
                    if(!match && !cacheColors.isEmpty() && dataColor.matches(cacheColors)) match = true;
                    cacheColors.addAll(dataColor.getValue());
                }
                
                gainPower(target, match ? -2000 : -3000, ChronoDuration.turnEnd());
            }
        }
    }
}
