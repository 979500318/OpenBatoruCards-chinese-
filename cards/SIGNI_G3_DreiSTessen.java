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
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.stock.StockAbilityShadow;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class SIGNI_G3_DreiSTessen extends Card {

    public SIGNI_G3_DreiSTessen()
    {
        setImageSets("SPDi43-22");
        setLinkedImageSets("SPDi43-16");

        setOriginalName("ドライ＝Sテッセン");
        setAltNames("ドライエステッセン Dorei Esu Tessen");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に《VOGUE3-EXTREME サンガ》がいる場合、色１つを宣言し、あなたのエナゾーンから宣言した色を持つカード１枚をトラッシュに置いてもよい。そうした場合、次の対戦相手のターン終了時まで、このシグニは[[シャドウ（{{宣言した色のシグニ$%1のシグニ}}）]]を得、追加で宣言した色を得る。\n" +
                "@U：このシグニがアタックしたとき、このシグニと共通する色を持つ対戦相手のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Drei-S Tessen");
        setDescription("en",
                "@U: At the beginning of your attack phase, if your LRIG is \"Sanga, VOGUE 3-EXTREME\", declare a color and you may put 1 card with the declared color from your ener zone into the trash. If you do, until the end of your opponent's next turn, this SIGNI gains [[Shadow ({{SIGNI of the declared color$%1 SIGNI}})]], and additionally gains the declared color.\n" +
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI that shares a common color with this SIGNI, and you may pay %X. If you do, banish it."
        );

		setName("zh_simplified", "DREI=S铁线莲");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，你的场上有《VOGUE3-EXTREME:サンガ》的场合，颜色1种宣言，可以从你的能量区把持有宣言颜色的牌1张放置到废弃区。这样做的场合，直到下一个对战对手的回合结束时为止，这只精灵得到[[暗影（宣言颜色的精灵）]]，追加得到宣言颜色。\n" +
                "@U :当这只精灵攻击时，持有与这只精灵共通颜色的对战对手的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("VOGUE3-EXTREME サンガ"))
            {
                CardColor color = playerChoiceColor();
                
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().withColor(color).fromEner()).get();
                if(trash(cardIndex))
                {
                    StockAbilityShadow attachedStock = new StockAbilityShadow(
                        cardIndexSource -> CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                                           cardIndexSource.getIndexedInstance().getColor().matches(color) ? ConditionState.OK : ConditionState.BAD,
                        color::getLabel
                    );
                    GFXCardTextureLayer.attachToAbility(attachedStock.getAbility(), new GFXCardTextureLayer(getCardIndex(), new GFXTextureCardCanvas("colors/"+color.toString().toLowerCase()+"_icon", 1,2)));
                    attachAbility(getCardIndex(), attachedStock, ChronoDuration.nextTurnEnd(getOpponent()));
                    
                    gainValue(getCardIndex(), getColor(),color, ChronoDuration.nextTurnEnd(getOpponent()));
                }
            }
        }

        private void onAutoEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withColor(getColor())).get();
            if(target != null && payEner(Cost.colorless(1)))
            {
                banish(target);
            }
        }
    }
}
