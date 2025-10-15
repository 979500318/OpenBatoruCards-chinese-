package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G2_CodeEatBackFatMashimashi extends Card {

    public SIGNI_G2_CodeEatBackFatMashimashi()
    {
        setImageSets("WXDi-P09-TK02A");

        setOriginalName("コードイート セアブラマシマシ");
        setAltNames("コードイートセアブラマシマシ Koodo Iito Seaburamashimashi");
        setDescription("jp",
                "((このクラフトは効果以外によっては場に出せない))\n" +
                "((【アクセ】はシグニ１体に１枚までしか付けられない。このクラフトが付いているシグニが場を離れるとこのクラフトは除外される))\n\n" +
                "@C：これにアクセされているシグニは【ランサー】を得る。"
        );
        
        setName("en", "Back Fat Mashimashi, Code: Eat");
        setDescription("en",
                "((This Craft can only be put onto the field by an effect.))\n" +
                "((Up to one [[Acce]] can be attached to a SIGNI, and it is removed from the game when that SIGNI leaves the field.))\n\n" +
                "@C: The SIGNI with this attached to it as an [[Acce]] gains [[Lancer]]."
        );
        
        setName("en_fan", "Code Eat Back Fat Mashimashi");
        setDescription("en_fan",
                "((This craft cannot enter the field other than by effects.))\n" +
                "((A SIGNI can only have up to 1 [[Accessory]] attached to it. This craft is excluded from the game when the SIGNI it is attached to leaves the field.))\n\n" +
                "@C: The SIGNI accessorized with this card gains [[Lancer]]."
        );
        
		setName("zh_simplified", "食用代号 背脂双倍拉面");
        setDescription("zh_simplified", 
                "@>@C :被此牌附属的精灵得到[[枪兵]]。\n" +
                "（当持有[[枪兵]]的精灵战斗把精灵破坏时，对战对手的生命护甲1张击溃）@@\n"
        );

        setCardFlags(CardFlag.CRAFT);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.COOKING);
        setLevel(2);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ConstantAbility cont = registerConstantAbility(new TargetFilter().SIGNI().over(cardId), new AbilityGainModifier(this::onConstEffModGetSample));
            cont.setActiveUnderFlags(GameConst.CardUnderType.ATTACHED_ACCESSORY);
        }
        
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityLancer());
        }
    }
}
