package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.LifeBurst;

public final class SPELL_R_PowerfulClawOfSeverance extends Card {

    public SPELL_R_PowerfulClawOfSeverance()
    {
        setImageSets("WXK01-051");

        setOriginalName("絶縁の豪爪");
        setAltNames("ゼツエンノゴウソウ Zetsuen no Gousou");
        setDescription("jp",
                "対戦相手のライフクロスが２枚以上ある場合、対戦相手のライフクロス１枚をクラッシュする。あなたの場にドライブ状態のシグニがある場合、この効果でクラッシュされたカードのライフバーストは発動しない。"
        );

        setName("en", "Powerful Claw of Severance");
        setDescription("en",
                "If your opponent has 2 or more life cloth, crush 1 of your opponent's life cloth. If there is a SIGNI in the drive state on your field, the ## @[Life Burst]@ of the card crushed by this effect doesn't activate."
        );

		setName("zh_simplified", "绝缘的豪爪");
        setDescription("zh_simplified", 
                "对战对手的生命护甲在2张以上的场合，对战对手的生命护甲1张击溃。你的场上有驾驶状态的精灵的场合，这个效果击溃的牌的生命迸发不能发动。\n"
        );

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 3));

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerSpellAbility(this::onSpellEff);
        }
        
        private void onSpellEff()
        {
            if(getLifeClothCount(getOpponent()) >= 2)
            {
                crush(getOpponent(), new TargetFilter().own().SIGNI().drive().getValidTargetsCount() > 0 ? LifeBurst.IGNORE : LifeBurst.ACTIVATE);
            }
        }
    }
}
