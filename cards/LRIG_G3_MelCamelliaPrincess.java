package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.stock.StockAbilitySLancer;

public final class LRIG_G3_MelCamelliaPrincess extends Card {

    public LRIG_G3_MelCamelliaPrincess()
    {
        setImageSets("WXDi-P12-009", "WXDi-P12-009U");

        setOriginalName("メル＝椿姫");
        setAltNames("メルツバキヒメ Meru Tsubakihime");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニが#Sの場合、シグニ１体を対象とし、ターン終了時まで、それのパワーを＋5000する。%G %Xを支払ってもよい。そうした場合、ターン終了時まで、それは【Ｓランサー】を得る。\n" +
                "@A %G %X %X @[エクシード４]@：あなたのデッキをシャッフルし一番上のカードをライフクロスに加える。"
        );

        setName("en", "Mel - Lady of the Camellias");
        setDescription("en",
                "@U: At the beginning of your attack phase, if all the SIGNI on your field are #S, target SIGNI on your field gets +5000 power until end of turn. You may pay %G %X. If you do, it gains [[S Lancer]] until end of turn.\n@A %G %X %X @[Exceed 4]@: Shuffle your deck and add the top card of your deck to your Life Cloth."
        );
        
        setName("en_fan", "Mel-Camellia Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if all of your SIGNI are #S @[Dissona]@ SIGNI, target 1 of your SIGNI, and until end of turn, it gets +5000 power. You may pay %G %X. If you do, until end of turn, it gains [[S Lancer]].\n" +
                "@A %G %X %X @[Exceed 4]@: Shuffle your deck and add the top card of your deck to life cloth."
        );

		setName("zh_simplified", "梅露=椿姫");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，你的场上的全部的精灵是#S的场合，精灵1只作为对象，直到回合结束时为止，其的力量+5000。可以支付%G%X。这样做的场合，直到回合结束时为止，其得到[[S枪兵]]。\n" +
                "@A %G%X %X@[超越 4]@:你的牌组洗切把最上面的牌加入生命护甲。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MEL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);
        setCoins(+2);

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
            
            registerActionAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(2)),
                new ExceedCost(4)
            ), this::onActionEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getSIGNICount(getOwner()) > 0 && new TargetFilter().own().SIGNI().not(new TargetFilter().dissona()).getValidTargetsCount() == 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
                
                if(target != null)
                {
                    gainPower(target, 5000, ChronoDuration.turnEnd());
                    
                    if(payEner(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)))
                    {
                        attachAbility(target, new StockAbilitySLancer(), ChronoDuration.turnEnd());
                    }
                }
            }
        }
        
        private void onActionEff()
        {
            shuffleDeck();
            addToLifeCloth(1);
        }
    }
}
