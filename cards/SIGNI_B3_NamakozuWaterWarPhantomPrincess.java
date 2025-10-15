package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.CrushCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B3_NamakozuWaterWarPhantomPrincess extends Card {

    public SIGNI_B3_NamakozuWaterWarPhantomPrincess()
    {
        setImageSets("WX24-P3-052");
        setLinkedImageSets("WX24-P3-022");

        setOriginalName("幻闘水姫　ナマコズ");
        setAltNames("ゲントウスイヒメナマコズ Gentousuihime Namakozu");
        setDescription("jp",
                "@C：このシグニのパワーはあなたのライフクロス１枚につき－2000される。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたの場に《エルドラ×マーク?　BURST》がいる場合、対戦相手のシグニ１体を対象とし、手札から＜水獣＞のシグニを好きな枚数捨てる。ターン終了時まで、それのパワーをこの方法で捨てたカード１枚につき－8000する。\n" +
                "@E @[ライフクロス１枚をクラッシュする]@：カードを１枚引く。"
        );

        setName("en", "Namakozu, Water War Phantom Princess");
        setDescription("en",
                "@C: This SIGNI gets --2000 power for each of your life cloth.\n" +
                "@U: At the beginning of your attack phase, if your LRIG is \"Eldora×Mark III BURST\", target 1 of your opponent's SIGNI, and you may discard any number of <<Water Beast>> SIGNI from your hand. Until end of turn, it gets --8000 power for each card discarded this way.\n" +
                "@E @[Crush 1 of your life cloth]@: Draw 1 card."
        );

		setName("zh_simplified", "幻斗水姬 海参和鲶鱼");
        setDescription("zh_simplified", 
                "@C :这只精灵的力量依据你的生命护甲的数量，每有1张就-2000。\n" +
                "@U :你的攻击阶段开始时，你的场上有《エルドラ×マークⅢ　BURST》的场合，对战对手的精灵1只作为对象，从手牌把<<水獣>>精灵任意张数舍弃。直到回合结束时为止，其的力量依据这个方法舍弃的牌的数量，每有1张就-8000。\n" +
                "@E 生命护甲1张击溃:抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(3);
        setPower(16000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new PowerModifier(this::onConstEffModGetValue));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(new CrushCost(1), this::onEnterEff);
        }
        
        private double onConstEffModGetValue(CardIndex cardIndex)
        {
            return -2000 * getLifeClothCount(getOwner());
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("エルドラ×マークⅢ　BURST"))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                
                if(target != null)
                {
                    DataTable<CardIndex> data = discard(0,AbilityConst.MAX_UNLIMITED, new TargetFilter().SIGNI().withClass(CardSIGNIClass.WATER_BEAST));
                    
                    if(data.get() != null)
                    {
                        gainPower(target, -8000 * data.size(), ChronoDuration.turnEnd());
                    }
                }
            }
        }
        
        private void onEnterEff()
        {
            draw(1);
        }
    }
}
